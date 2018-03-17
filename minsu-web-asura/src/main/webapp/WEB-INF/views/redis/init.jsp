<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include
	file="/include.jsp"%>


<script type="text/javascript">
    function doFind(json) {
        var keyValue = $('#redisKey').val();
        var url = 'redis/mg/search.do?keyValue='+keyValue;
        $('#showDiv').loadUrl(url);
    }
</script>


<div class="pageContent">
	<div  layoutH="56"  style="height: 120px; overflow: auto;">
        <p>
            <label>redis-key：${keyPre}</label>
            <input type="text" class="required" id="redisKey" name="redisKey" value="" lookupGroup="orgLookup" />
            <a class="btnLook" id="D_orgLookup" onclick="doFind()"  >查找redis数据</a>
        </p>
        <p>
        <div id="showDiv"  class="pageContent">

        </div>
        </p>
	 </div>

</div>