function zy_for(e, cb){
	var ch;
	if(e.currentTarget)
    	ch = e.currentTarget.previousElementSibling;
	else
		ch = e.previousElementSibling;
  if (ch.nodeName == "INPUT") {
      if (ch.type == "checkbox") 
          ch.checked = !ch.checked;
      if (ch.type == "radio" && !ch.checked) 
          ch.checked = "checked";
      
  }
  if (cb) 
      cb(e, ch.checked);
}
$(function(){
	var width = '';
	$('.fangyuan dd>ul').find('li').each(function(){
		width = Number(width)+Number($(this).outerWidth(true));
	});
	$('.fangyuan dd>ul').width(width+'px');

	var count = '';
	count = $('#fuwu ul li').length;
	if(count%2==0){
		$('#fuwu ul li').eq(count-2).css('borderBottom','none');
	}
})