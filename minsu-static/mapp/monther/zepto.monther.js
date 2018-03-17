(function ($) {
    $.fn.monther = function(opts){
		var defaults = {
			// dateStart : new Date(),
			title : '',
			yearStart : 0,
			yearEnd : null,
			// dateNum : 10,
			// timeStart : 9,
			// timeNum : 12,
			onOk : null,
			onCancel : null,
		};
		var option = $.extend(defaults, opts);

		var input = $(this),
			itemHeight =  $('#houseBaseDataShow').height();
		var picker = {
			input : null,
			init : function(){
				var _this = this;

				//初始化点击input事件
				input.on('tap', function(){
					_this.input = $(this);
					if($('.mt_mask').length){
						_this.hidePanel();
					}else{
						_this.renderHTML();//添加html
						var container = $('.mt_poppanel'),
							box_s = $('.mt_s', container),
							box_t = $('.mt_t', container),
							box_c = $('.mt_c', container),
							box_w = $('.mt_w', container),
							box_y = $('.mt_y', container);

						_this.afterRenderHTML(container,box_s,box_t,box_c,box_w,box_y);//生成日期选择和滑动事件
						_this.pancelBind(container,box_s,box_t,box_c,box_w,box_y);//对html绑定日期选择事件
						_this.showPanel();
						//初始化原有的数据
						_this.setValue();
					}
				});
			},
			renderHTML : function(){
				//var stime = option.timeStart + ':00';
				//var etime = option.timeStart + option.timeNum + ':00';
				var html = '<div class="mt_mask"></div>'
					+'<div id="mtimer" class="mt_poppanel">'
						+'<div class="mt_panel">'
							+'<div class="mt_body">'
								+'<div class="mt_s">'
								+'<ul id="mt_s"><li></li></ul>'
							+'</div>'
							+'<div class="mt_t">'
								+'<ul id="mt_t"><li></li></ul>'
							+'</div>'
							+'<div class="mt_c">'
								+'<ul id="mt_c"><li></li></ul>'
							+'</div>'
							+'<div class="mt_w">'
								+'<ul id="mt_w"><li></li></ul>'
							+'</div>'
							+'<div class="mt_y">'
								+'<ul id="mt_y"><li></li></ul>'
							+'</div>'
							+'<div class="mt_indicate"></div>'
						+'</div>'
						+'<div class="mt_confirm">'
							+'<a href="javascript:void(0);" class="mt_ok">确定</a>'
							+'<a href="javascript:void(0);" class="mt_cancel">取消</a>'
						+'</div>'
					+'</div>'
				+'</div>';
				$(document.body).append(html);
			},
			afterRenderHTML : function (container,box_s,box_t,box_c,box_w,box_y) {
				var _this = this;
				//初始化date
				var s = '', //室
					t ='',  //厅
					c ='',  //厨
					w ='',  //卫
					y = ''; //阳

					s += '<li></li>';
					t += '<li></li>';
					c += '<li></li>';
					w += '<li></li>';
					y += '<li></li>';

				var n_s = parseInt($("#sIpt").val()) ? parseInt($("#sIpt").val()) : 0;
				var n_t = parseInt($("#tIpt").val()) ? parseInt($("#tIpt").val()) : 0;
				var n_c = parseInt($("#cIpt").val()) ? parseInt($("#cIpt").val()) : 0;
				var n_w = parseInt($("#wIpt").val()) ? parseInt($("#wIpt").val()) : 0;
				var n_y = parseInt($("#yIpt").val()) ? parseInt($("#yIpt").val()) : 0;


				
					
				for(var i=0; i<=50; i++){
					sel = i == n_s ? 'selected' : '';
					s += '<li class="'+sel+'" data-date="'+i+'室">'+i+'室</li>';
				}
				for(var i=0; i<=50; i++){
					sel = i == n_t ? 'selected' : '';
					t += '<li class="'+sel+'" data-date="'+i+'厅">'+i+'厅</li>';
				}
				for(var i=0; i<=50; i++){
					sel = i == n_c ? 'selected' : '';
					c += '<li class="'+sel+'" data-date="'+i+'厨">'+i+'厨</li>';
				}
				for(var i=0; i<=50; i++){
					sel = i == n_w ? 'selected' : '';
					w += '<li class="'+sel+'" data-date="'+i+'卫">'+i+'卫</li>';
				}
				for(var i=0; i<=50; i++){
					sel = i == n_y ? 'selected' : '';
					y += '<li class="'+sel+'" data-date="'+i+'阳">'+i+'阳</li>';
				}
				s += '<li></li><li></li>';
				t += '<li></li><li></li>';
				c += '<li></li><li></li>';
				w += '<li></li><li></li>';
				y += '<li></li><li></li>';

				box_s.find('ul').append(s);
				box_t.find('ul').append(t);
				box_c.find('ul').append(c);
				box_w.find('ul').append(w);
				box_y.find('ul').append(y);
				
				

				

				//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
				//初始化scroll
				var elHeight = $('#roomMessage').height();
				
				// console.log(elHeight);
				var boxSscroll = new IScroll('.mt_s', {
					snap : 'li',
					probeType : 2,
					tap : true
				});

				
				
				boxSscroll.on('scroll', function(){
					_this.updateSelected(box_s, this);
				});
				boxSscroll.on('scrollEnd', function(){
					_this.updateSelected(box_s, this);
				});


				var boxTscroll = new IScroll('.mt_t', {
					snap : 'li',
					probeType : 2,
					tap : true
				});
				boxTscroll.on('scroll', function(){
					_this.updateSelected(box_t, this);
				});
				boxTscroll.on('scrollEnd', function(){
					_this.updateSelected(box_t, this);
				});

				var boxCscroll = new IScroll('.mt_c', {
					snap : 'li',
					probeType : 2,
					tap : true
				});
				boxCscroll.on('scroll', function(){
					_this.updateSelected(box_c, this);
				});
				boxCscroll.on('scrollEnd', function(){
					_this.updateSelected(box_c, this);
				});

				var boxWscroll = new IScroll('.mt_w', {
					snap : 'li',
					probeType : 2,
					tap : true
				});
				boxWscroll.on('scroll', function(){
					_this.updateSelected(box_w, this);
				});
				boxWscroll.on('scrollEnd', function(){
					_this.updateSelected(box_w, this);
				});

				var boxYscroll = new IScroll('.mt_y', {
					snap : 'li',
					probeType : 2,
					tap : true
				});
				boxYscroll.on('scroll', function(){
					_this.updateSelected(box_y, this);
				});
				boxYscroll.on('scrollEnd', function(){
					_this.updateSelected(box_y, this);
				});

				this.boxSscroll = boxSscroll;
				this.boxTscroll = boxTscroll;
				this.boxCscroll = boxCscroll;
				this.boxWscroll = boxWscroll;
				this.boxYscroll = boxYscroll;


				boxSscroll.scrollTo(0, -n_s*itemHeight, 100, IScroll.utils.ease.elastic);
				boxTscroll.scrollTo(0, -n_t*itemHeight, 100, IScroll.utils.ease.elastic);
				boxCscroll.scrollTo(0, -n_w*itemHeight, 100, IScroll.utils.ease.elastic);
				boxWscroll.scrollTo(0, -n_c*itemHeight, 100, IScroll.utils.ease.elastic);
				boxYscroll.scrollTo(0, -n_y*itemHeight, 100, IScroll.utils.ease.elastic);

			},
			pancelBind : function (container,box_s,box_t,box_c,box_w,box_y) {
				var _this = this;
				//初始化点击li
				box_s.find('li').on('tap', function(){
					_this.checkDate($(this));
				});
				box_t.find('li').on('tap', function(){
					_this.checkTime($(this));
				});
				//初始化点击事件
				$('.mt_ok', container).on('tap', function(){
					var s = box_s.find('.selected').html();
					var s2 = Number(s.replace('室',''));
					var t = box_t.find('.selected').html();
					var t2 = Number(t.replace('厅',''));
					var c = box_c.find('.selected').html();
					var c2 = Number(c.replace('厨',''));
					var w = box_w.find('.selected').html();
					var w2 = Number(w.replace('卫',''));
					var y = box_y.find('.selected').html();
					var y2 = Number(y.replace('阳',''));
					$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");

					$("#sIpt").val(s2);
					$("#tIpt").val(t2);
					$("#cIpt").val(c2);
					$("#wIpt").val(w2);
					$("#yIpt").val(y2);
					_this.input.val(s+''+t+''+c+''+w+''+y);
					_this.hidePanel();
					option.onOk && typeof option.onOk=='function' && option.onOk(container);
				});
				$('.mt_cancel', container).on('tap', function(){
					_this.hidePanel();
					option.onCancel && typeof option.onCancel=='function' && option.onCancel(container);
				});
				$('.mt_mask').on('tap', function(){
					_this.hidePanel();
				});
			},
			updateSelected : function(container, iscroll){
				var index = (-iscroll.y) / itemHeight + 2;
				var current = container.find('li').eq(index);
				current.addClass('selected').siblings().removeClass('selected');
			},
			showPanel : function(container){
				$('.mt_poppanel, .mt_mask').addClass('show');
			},
			hidePanel : function(){
				$('.mt_poppanel, .mt_mask').remove();
			},
			setValue : function(){
				var value = this.input.val();
				var dateArr = value.split(' '),
					date = dateArr[0],
					time = dateArr[1],
					dateItem = $('.mt_date li[data-date="'+date+'"]'),
					timeItem = $('.mt_time li[data-time="'+time+'"]');
				this.checkDate(dateItem);
				this.checkTime(timeItem);
			},
			checkDate : function(el){
				var target = el.prev('li').prev('li');
				this.boxSscroll.scrollToElement(target[0]);
			},
			checkTime : function(el){
				var target = el.prev('li').prev('li');
				this.boxTscroll.scrollToElement(target[0]);
			}
		}
		picker.init();
		return picker;
	}
	return $.fn.monther;
})(Zepto);
