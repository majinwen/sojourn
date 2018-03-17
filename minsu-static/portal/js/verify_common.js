	// 验证身份证  
	function isCardNo(card) {  
	 var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	 return pattern.test(card);  
	}  
	
	// 验证护照
	function isPassport(card) {  
	 var re1 = /^[a-zA-Z]{5,17}$/;  
	 var re2 = /^[a-zA-Z0-9]{5,17}$/;    
	 return re1.test(card) || re2.test(card);  
	}  
	// 港澳通行证验证
	function isHKMacao(card) {  
	 var re = /^[HMhm]{1}([0-9]{10}|[0-9]{8})$/; 
	 return re.test(card);  
	}  
	
	// 台湾通行证验证
	function isTaiwan(card) {  
	 var re1 = /^[0-9]{8}$/;  
	 var re2 = /^[0-9]{10}$/; 
	 return re1.test(card) || re2.test(card);
	}
	
	/**
	 * 验证手机号
	 * @param mobile
	 * @returns {Boolean}
	 */
	function validatemobile(mobile) 
	   { 
	       if(mobile.length==0) 
	       { 
	          return false; 
	       }     
	       if(mobile.length!=11) 
	       { 
	           return false; 
	       } 
	        
	       var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	       if(!myreg.test(mobile)) 
	       { 
	           return false; 
	       }
	       return true;
	   } 