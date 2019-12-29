$(function () {

  $(".progress").each(function () {

    var value = $(this).attr('data-value');
    var left = $(this).find('.progress-left .progress-bar');
    var right = $(this).find('.progress-right .progress-bar');

    if (value > 0) {
      right.css("transition", "all linear 2s");
      if (value <= 50) {
        right.css('transform', 'rotate(' + percentageToDegrees(value) + 'deg)')
      } else {
        right.css('transform', 'rotate(180deg)');
        left.css('transform',
            'rotate(' + percentageToDegrees(value - 50) + 'deg)');
        left.css("transition", "all linear 1s");
        left.css("transition-delay", " 2s");
      }

    }

  });

  function percentageToDegrees(percentage) {

    return percentage / 100 * 360

  }

  //toggle sidebar
  $("#toggle-sidebar").click(function () {
    $(".page-wrapper").toggleClass("toggled");
    $("#toggle-sidebar").toggleClass("toggled");
  });

  //toggle sidebar overlay
  $("#overlay").click(function () {
    $(".page-wrapper").toggleClass("toggled");
    $("#toggle-sidebar").toggleClass("toggled");
  });

  $('.toast').toast('show')
});
