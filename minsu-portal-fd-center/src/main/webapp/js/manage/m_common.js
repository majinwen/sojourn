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
	
	//IE8把placeholeder变成单独元素
	if (!placeholderSupport()) { // 判断浏览器是否支持 placeholder
		 
      $('[placeholder]').each(function(){
    	  var input=$(this);
    	  var left=input.position().left;
    	  var top=input.position().top;
    	  var h=input.height();
    	  var w=input.width();
    	  var p=$('<p>'+input.attr('placeholder')+'</p>');
    	  if(this.type=='text'){
	    	  p.css({
	    		  'font-size':'14px',
	    		  'position':'absolute',
	    		  'left':left+'px',
	    		  'top':top+'px',
	    		  'line-height':h+'px',
	    		  'padding-left':'15px',
	    		  'color':'#bbb'
	    	  });
    	  }
    	  else if(this.type=='textarea'){
    		  p.css({
	    		  'font-size':'14px',
	    		  'position':'absolute',
	    		  'left':left+'px',
	    		  'top':top+'px',
	    		  'width':w+'px',
	    		  'line-height':'25px',
	    		  'padding':'18px',
	    		  'color':'#bbb'
	    	  });
    	  }
    	  p.click(function(){
    		  $(this).next().focus();
    	  });
    	  p.insertBefore(input);
      }).focus(function() {
    	  $(this).prev().hide();
      }).blur(function() {
    	  var _this=$(this);
    	  setTimeout(function(){
    		  if(_this.val()==''){
        		  _this.prev().show();
        	  }
    		  else{
    			  _this.prev().hide();
    		  }
		  },100);
      }).blur();
    };
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