$(function(){
	// 左侧菜单栏选中
	$(".part_l_list dd").each(function(){
		$(this).click(function(){
			$(this).parents(".part_l_box").find(".part_l_list dd").removeClass("active");
			$(this).addClass("active");
		})
	})

})
// 头像上传
$(function () {

	'use strict';

	var console = window.console || { log: function () {} };
	var $image = $('#image');
  // var $download = $('#download');
  // var $dataX = $('#dataX');
  // var $dataY = $('#dataY');
  // var $dataHeight = $('#dataHeight');
  // var $dataWidth = $('#dataWidth');
  // var $dataRotate = $('#dataRotate');
  // var $dataScaleX = $('#dataScaleX');
  // var $dataScaleY = $('#dataScaleY');
  var options = {
  	aspectRatio: 1/1,
  	preview: '.img-preview',
        // crop: function (e) {
        //   $dataX.val(Math.round(e.x));
        //   $dataY.val(Math.round(e.y));
        //   $dataHeight.val(Math.round(e.height));
        //   $dataWidth.val(Math.round(e.width));
        //   $dataRotate.val(e.rotate);
        //   $dataScaleX.val(e.scaleX);
        //   $dataScaleY.val(e.scaleY);
        // }
    };


  // Tooltip
  $('[data-toggle="tooltip"]').tooltip();


  // Cropper
  $image.on({
  	'build.cropper': function (e) {
      // console.log(e.type);
  },
  'built.cropper': function (e) {
      // console.log(e.type);
  },
  'cropstart.cropper': function (e) {
      // console.log(e.type, e.action);
  },
  'cropmove.cropper': function (e) {
      // console.log(e.type, e.action);
  },
  'cropend.cropper': function (e) {
      // console.log(e.type, e.action);
  },
  'crop.cropper': function (e) {
      // console.log(e.type, e.x, e.y, e.width, e.height, e.rotate, e.scaleX, e.scaleY);
      // 图片位置
      var _w = e.width;
      var _h = e.height;
      var _x = e.x;
      var _y = e.y;
      var _position = _w+","+_h+","+_x+","+_y;
      $("#imgPosition").val(_position);
  },
  'zoom.cropper': function (e) {
      // console.log(e.type, e.ratio);
  }
}).cropper(options);


  // Options
  $('.docs-toggles').on('change', 'input', function () {
  	var $this = $(this);
  	var name = $this.attr('name');
  	var type = $this.prop('type');
  	var cropBoxData;
  	var canvasData;

  	if (!$image.data('cropper')) {
  		return;
  	}

  	if (type === 'checkbox') {
  		options[name] = $this.prop('checked');
  		cropBoxData = $image.cropper('getCropBoxData');
  		canvasData = $image.cropper('getCanvasData');

  		options.built = function () {
  			$image.cropper('setCropBoxData', cropBoxData);
  			$image.cropper('setCanvasData', canvasData);
  		};
  	} else if (type === 'radio') {
  		options[name] = $this.val();
  	}

  	$image.cropper('destroy').cropper(options);
  });


  // Methods
  $('.docs-buttons').on('click', '[data-method]', function () {
  	var $this = $(this);
  	var data = $this.data();
  	var $target;
  	var result;

  	if ($this.prop('disabled') || $this.hasClass('disabled')) {
  		return;
  	}

  	if ($image.data('cropper') && data.method) {
      data = $.extend({}, data); // Clone a new one

      if (typeof data.target !== 'undefined') {
      	$target = $(data.target);

      	if (typeof data.option === 'undefined') {
      		try {
      			data.option = JSON.parse($target.val());
      		} catch (e) {
      			console.log(e.message);
      		}
      	}
      }

      result = $image.cropper(data.method, data.option, data.secondOption);

      switch (data.method) {
      	case 'scaleX':
      	case 'scaleY':
      	$(this).data('option', -data.option);
      	break;

      	case 'getCroppedCanvas':
      	if (result) {

            // Bootstrap's Modal
            $('#getCroppedCanvasModal').modal().find('.modal-body').html(result);

            if (!$download.hasClass('disabled')) {
            	$download.attr('href', result.toDataURL('image/jpeg'));
            }
        }

        break;
    }

    if ($.isPlainObject(result) && $target) {
    	try {
    		$target.val(JSON.stringify(result));
    	} catch (e) {
    		console.log(e.message);
    	}
    }

}
});


  // Keyboard
  $(document.body).on('keydown', function (e) {

  	if (!$image.data('cropper') || this.scrollTop > 300) {
  		return;
  	}

  	switch (e.which) {
  		case 37:
  		e.preventDefault();
  		$image.cropper('move', -1, 0);
  		break;

  		case 38:
  		e.preventDefault();
  		$image.cropper('move', 0, -1);
  		break;

  		case 39:
  		e.preventDefault();
  		$image.cropper('move', 1, 0);
  		break;

  		case 40:
  		e.preventDefault();
  		$image.cropper('move', 0, 1);
  		break;
  	}

  });


  // Import image
  var $inputImage = $('#inputImage');
  // var URL = window.URL || window.webkitURL;
  var blobURL;
  // if (URL) {
  	$inputImage.change(function () {
      uploadImg("inputImage");
      $("#clipArea").show();
      $("#btnBox").removeClass("up_btn_box").addClass("down_btn_box");
      $("#uploadBtn").val("重新上传");
      blobURL = $("#newImage").val();
      $image.one('built.cropper', function () {
      }).cropper('reset').cropper('replace', blobURL);
      $inputImage.val('');
  });
  // }
});

function uploadImg(id) {
			// debugger

			var f = $("#"+id).val();                       
			if (!/\.(jpg|png|JPG|PNG)$/.test(f)) {
//				alert("图片格式不正确");

} else {
	var options = {
		success: function(txt) {
			var txt = eval('(' + txt + ')');
			if (txt.code == 0) {
				$("#clipArea").show();
				$("#btnBox").removeClass("up_btn_box").addClass("down_btn_box");
				$("#uploadBtn").val("重新上传");
							// 图片路径
							$("#newImage").val(txt.data.headUrl);
							$("#image").attr('src',txt.data.headUrl);
							
						} else {
							hideClipBox();
						}
					}
				};
				$("#"+id).parents("form").ajaxSubmit(options);
				return false;
			}
		}
		function showModal(){
			$("#headImgModal").show();
			$("#btnBox").removeClass("down_btn_box").addClass("up_btn_box");
			$("#uploadBtn").val("");
      $("body").addClass("hidden");
		}
		function showClipBox(){
			$("#clipArea").show();
			$("#btnBox").removeClass("up_btn_box").addClass("down_btn_box");
			$("#uploadBtn").val("重新上传");
			
		}
		function closeModal(){
			$("#headImgModal").hide();
			$("#clipArea").hide();
			$("#btnBox").removeClass("down_btn_box").addClass("up_btn_box");
			$("#uploadBtn").val("");	
      $("body").removeClass("hidden");
		}
// 保存头像
function saveHeadImg(){
	// 图片位置和宽高
	var img_position = $("#imgPosition").val();
	// TODO:保存图片 设置头像路径
	closeModal();
	$("#headImg").attr("src");
}
