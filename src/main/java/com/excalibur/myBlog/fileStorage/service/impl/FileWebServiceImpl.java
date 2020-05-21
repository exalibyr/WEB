package com.excalibur.myBlog.fileStorage.service.impl;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.response.ResponseBody;
import com.excalibur.myBlog.dao.response.ResponseBodyException;
import com.excalibur.myBlog.fileStorage.configuration.FileStorageConfiguration;
import com.excalibur.myBlog.fileStorage.entity.FileStorageResponseBody;
import com.excalibur.myBlog.fileStorage.service.FileWebService;
import com.excalibur.myBlog.utils.ApplicationUtils;
import com.excalibur.myBlog.utils.entity.Image;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileWebServiceImpl implements FileWebService {


    @Autowired
    private ObjectMapper mapper = new ObjectMapper();
    private SimpleClientHttpRequestFactory httpRequestFactory;
    private RestTemplate restTemplate;

    public FileWebServiceImpl() {
        this.httpRequestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate = new RestTemplate(this.httpRequestFactory);
        this.restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        this.restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter(this.mapper));
        this.restTemplate.getMessageConverters().add(new ResourceHttpMessageConverter());
    }

    @Override
    public String postFile(MultipartFile multipartFile, Integer userId) throws ResponseBodyException, ResponseStatusException, IOException {
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("file", multipartFile);
//        RequestEntity<MultiValueMap<String, Object>> requestEntity = new RequestEntity<>(body, getHeaders(), HttpMethod.POST, generateURI(userId));
        Map<String, String> params = new HashMap<>();
        params.put("mediaType", multipartFile.getContentType());
        RequestEntity<byte[]> requestEntity = new RequestEntity<>(
                multipartFile.getBytes(),
                getHeaders(MediaType.parseMediaType(multipartFile.getContentType())),
                HttpMethod.POST,
                setParams(generateURI(userId), params)
        );
        System.out.println(requestEntity.getUrl());
        ResponseEntity<FileStorageResponseBody> responseEntity = this.restTemplate.exchange(requestEntity, FileStorageResponseBody.class);
        switch (responseEntity.getStatusCode()) {
            case CREATED:
            case OK: {
                FileStorageResponseBody responseBody = responseEntity.getBody();
                if (responseBody.isSuccess() && ApplicationUtils.compare(responseBody.getKey(), userId)) {
                    return responseBody.getFileName();
                } else {
                    throw new ResponseBodyException("Invalid response body");
                }
            }
            default: {
                throw new ResponseStatusException(responseEntity.getStatusCode());
            }
        }
    }

    private HttpHeaders getHeaders(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return headers;
    }

    private URI generateURI(Integer userId) {
        return URI.create(FileStorageConfiguration.getFileStorageURL() + "/temp/user/" + ApplicationUtils.getEncryptedID(userId));
    }

    private URI setParams(URI uri, Map<String, String> params) {
        StringBuilder builder = new StringBuilder(uri.toString());
        builder.append("?");
        for (String paramName : params.keySet()) {
            builder.append(paramName).append("=").append(params.get(paramName)).append("&");
        }
        builder.delete(builder.length() - 1, builder.length());
        return URI.create(builder.toString());
    }


}
