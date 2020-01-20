package com.excalibur.myBlog.utils;

import com.excalibur.myBlog.dao.Publication;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ZonedDateTimeFormatter {

    public static List<PublicationWrapper> getFormattedPublications(List<Publication> publications) {
        return publications.isEmpty() ? new ArrayList<>() : publications.stream().map(
                publication -> new PublicationWrapper(publication, getLocalDateTime(publication.getDateTime()))
        ).collect(Collectors.toList());
    }

    public static String getLocalDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime
                .format(
                        DateTimeFormatter
                                .ofLocalizedDateTime(
                                        FormatStyle.LONG,
                                        FormatStyle.SHORT
                                )
                );

    }

}
