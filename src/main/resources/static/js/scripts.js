$(document).ready(function () {/* smooth scrolling for scroll to top */
    $('.scroll-top').click(function () {
        $('body,html').animate({scrollTop: 0}, 1000);
    })
    /* smooth scrolling for scroll down */
    $('.scroll-down').click(function () {
        $('body,html').animate({scrollTop: $(window).scrollTop() + 800}, 1000);
    })

    /* highlight the top nav as scrolling occurs */
    $('body').scrollspy({target: '#navbar'})


    var sampler = $('.header .sampler');

    $('li', sampler).on('click', function () {
        if ($(this).hasClass('selected')) {
            return false;
        }

        sampler.find('li').removeClass('selected');

        var target = $(this).attr('data-target');

        var code = sampler.find('.target-' + target).html();


        sampler.find('pre code.display').fadeOut(function () {
            $(this).html(code);
            $(this).fadeIn();
        });

        $(this).addClass('selected');
    });
});