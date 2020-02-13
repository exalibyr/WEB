$(document).ready(function() {

    var body = $("body");
    var contentWrapper = $(".content_wrap");
    var headerHolder = $(".content_margin_top");

    if (contentWrapper.height() < body.height() - headerHolder.height()) {
        contentWrapper.addClass('stretched');
        body.addClass('fixed');
    }

    if ($('div').is('#sign_up_validation')) {

        var username = $('#userLogin');
        var password = $('#userPassword');
        var passwordRepeat = $('#userPasswordRepeat');
        var name = $('#userName');
        var surname = $('#userSurname');

        validate('signUp');

        username.on('keypress keyup keydown', function() {
            validate('signUp');
        });

        password.on('keypress keyup keydown', function() {
            validate('signUp');
        });

        passwordRepeat.on('keypress keyup keydown', function() {
            validate('signUp');
        });

        name.on('keypress keyup keydown', function() {
            validate('signUp');
        });

        surname.on('keypress keyup keydown', function() {
            validate('signUp');
        });

    }

    if ($('div').is('#edit_profile_validation')) {

        var name = $('#name');
        var surname = $('#surname');

        validate('editProfile');

        name.on('keypress keyup keydown', function() {
            validate('editProfile');
        });

        surname.on('keypress keyup keydown', function() {
            validate('editProfile');
        });

    }

	var dropZone = $('#upload-container');

	if ($('form').is(dropZone)) {

        dropZone.on('drag dragstart dragend dragover dragenter dragleave drop', function(){
            return false;
        });

        dropZone.on('dragover dragenter', function() {
            dropZone.addClass('dragover');
        });

        dropZone.on('dragleave', function(e) {
            let dx = e.pageX - dropZone.offset().left;
            let dy = e.pageY - dropZone.offset().top;
            if ((dx < 0) || (dx > dropZone.width()) || (dy < 0) || (dy > dropZone.height())) {
                dropZone.removeClass('dragover');
            }
        });

        dropZone.on('drop', function(e) {
            dropZone.removeClass('dragover');
            let files = e.originalEvent.dataTransfer.files;
            sendFiles(files);
        });

        $('#file-input').change(function() {
            let files = this.files;
            sendFiles(files);
        });


    }


});

function validate(page) {

    if (page == 'signUp') {
        var username = $('#userLogin');
        var password = $('#userPassword');
        var passwordRepeat = $('#userPasswordRepeat');
        var name = $('#userName');
        var surname = $('#userSurname');
        var submitBtn = $('.submit_btn');
        var invalid = false;

        if (username.val().length > 4) {
            username.removeClass('invalid');
            username.addClass('valid');
        } else {
            username.removeClass('valid');
            username.addClass('invalid');
            invalid = true;
        }
        if (password.val().length > 4) {
            password.removeClass('invalid');
            password.addClass('valid');
        } else {
            password.removeClass('valid');
            password.addClass('invalid');
            invalid = true;
        }
        if (passwordRepeat.val().length > 4 && passwordRepeat.val() == password.val()) {
            passwordRepeat.removeClass('invalid');
            passwordRepeat.addClass('valid');
        } else {
            passwordRepeat.removeClass('valid');
            passwordRepeat.addClass('invalid');
            invalid = true;
        }
        if (name.val().length > 0) {
            name.removeClass('invalid');
            name.addClass('valid');
        } else {
            name.removeClass('valid');
            name.addClass('invalid');
            invalid = true;
        }
        if (surname.val().length > 0) {
            surname.removeClass('invalid');
            surname.addClass('valid');
        } else {
            surname.removeClass('valid');
            surname.addClass('invalid');
            invalid = true;
        }
        submitBtn.prop('disabled', invalid);
    } else if (page == 'editProfile') {

        var name = $('#name');
        var surname = $('#surname');
        var submitBtn = $('.submit_btn');
        var invalid = false;

        if (name.val().length > 0) {
            name.removeClass('invalid');
            name.addClass('valid');
        } else {
            name.removeClass('valid');
            name.addClass('invalid');
            invalid = true;
        }
        if (surname.val().length > 0) {
            surname.removeClass('invalid');
            surname.addClass('valid');
        } else {
            surname.removeClass('valid');
            surname.addClass('invalid');
            invalid = true;
        }
        submitBtn.prop('disabled', invalid);
    }
}

function sendFiles(files) {

    var dropZone = $('#upload-container');
//		let maxFileSize = 5242880;
    let Data = new FormData();
    $(files).each(function(index, file) {
//			if ((file.size <= maxFileSize) && ((file.type == 'image/png') || (file.type == 'image/jpeg'))) {
            Data.append('file', file);
//			};
    });

    $.ajax({
        url: dropZone.prop('action'),
        type: dropZone.prop('method'),
        data: Data,
        contentType: false,
        processData: false,
        crossDomain: true,
        success: function(data, textStatus, jqXHR) {
            console.log(data);
            sendFilesInfo(data);
        },
        error: function(jqXHR, textStatus, errorThrown ) {
            alert (errorThrown);
        },
        timeout: 30000,
        statusCode: {
            404: function () {
                console.log('resource not found 404');
            },
            500: function () {
                console.log('internal server error 500');
            },
            200: function() {
                console.log('status OK 200');
            },
            403: function() {
                console.log('Forbidden 403');
            },
            401: function() {
                console.log('Unauthorized 401');
            },
            400: function() {
                console.log('Bad request 400');
            }
        }
    });
}

function sendFilesInfo(data) {
    $.ajax({
        url: $('#callbackURI').prop('value'),
        type: 'POST',
        data: data,
        contentType: 'application/json; charset=UTF-8',
        beforeSend: function(request) {
            request.setRequestHeader("X-CSRF-TOKEN", $('input[name=_csrf]').prop('value'));
        },
        processData: false,
        crossDomain: false,
        success: function(data, textStatus, jqXHR) {
            console.log(data);
            if (data != null) {
                $('.avatar_img').prop('src', data);
            } else {
                alert('Ошибка при загрузке файла');
            }
        },
        error: function(jqXHR, textStatus, errorThrown ) {
            console.log(errorThrown);
        },
        timeout: 30000,
        statusCode: {
            404: function () {
                console.log('resource not found 404');
            },
            500: function () {
                console.log('internal server error 500');
            },
            200: function() {
                console.log('status OK 200');
            },
            403: function() {
                console.log('Forbidden 403');
            },
            401: function() {
                console.log('Unauthorized 401');
            },
            400: function() {
                console.log('Bad request 400');
            }
        }
    });
}
