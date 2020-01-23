$(document).ready(function() {
    var body = $("body");
    var contentWrapper = $(".content_wrap");
    var headerHolder = $(".content_margin_top");
    
    if (contentWrapper.height() < body.height() - headerHolder.height()) {
        contentWrapper.addClass('stretched');
        body.addClass('fixed');
    }
});
