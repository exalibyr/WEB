$(document).ready(function() {
    var screenHeight = window.screen.height;
    var contentWrapper = $(".content_wrap");
    if (contentWrapper.height() < screenHeight) {
        contentWrapper.addClass('stretched');
        $("body").addClass('fixed');
    }
});
