$(function(){
	// 左侧菜单栏选中
	$(".part_l_list dd").each(function(){
		$(this).click(function(){
			$(this).parents(".part_l_box").find(".part_l_list dd").removeClass("active");
			$(this).addClass("active");
		})
    })
    
  	//设置弹层高度
	$(window).resize(function() {
	  countModalHeight();
	});
	//关闭弹层
	$('.modal-content .close').click(function() {
	  closeModalBox();
	});
	$("#modal").on('click', function() {
	  $(this).hide();
	});
	$('.modal-content').on('click', function(event) {
	  event.stopPropagation();
	});
	
})
function showModal(){
  $("#headImgModal").show();
  $("body").addClass("hidden");
  var t = ($(window).height() - 600) / 2 ;
  t = t < 0 ? 0 : t;
  $("#headImgModal").find(".modal_main").css({
    'margin-top': t + 'px'
  });
}
function closeModal(){
  $("#headImgModal").hide();
  $("body").removeClass("hidden");
}
/*打开弹层*/
function showModalBox(id) {
  $('body').addClass('hidden');
  $('#modal').show();
  $('#' + id).show().siblings().hide();
  countModalHeight();
}

/*关闭弹层*/
function closeModalBox() {
  $('body').removeClass('hidden');
  $('#modal').hide();
}

/*计算弹层的高度*/
function countModalHeight() {
  var t = ($(window).height() - 200) / 2 ;
  t = t < 0 ? 0 : t;
  $('.modal-content').css({
    'margin-top': t + 'px'
  });
}