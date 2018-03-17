<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<script language="JavaScript" src='js/FusionCharts.js'></script>
<div class="pageFormContent nowrap" layoutH="0">
	<dl>
		<dt>服务器进程ID：</dt>
		<dd>
			${cacheStatus.pId }
		</dd>
	</dl>
	<dl>
		<dt>服务器运行秒数：</dt>
		<dd>
		    ${cacheStatus.upTime}
		</dd>
	</dl>
	<dl>
		<dt>服务器当前时间：</dt>
		<dd>
			${cacheStatus.nowDate }
		</dd>
	</dl>
	<dl>
		<dt>memcache版本:</dt>
		<dd>
			${cacheStatus.version }
		</dd>
	</dl>
	<dl>
		<dt>操作系统指针位数:</dt>
		<dd>
			${cacheStatus.pointerSize }
		</dd>
	</dl>
	<dl>
		<dt>系统累计用户时间:</dt>
		<dd>
			${cacheStatus.rusageUser }
		</dd>
	</dl>
	<dl>
		<dt>系统累计系统时间:</dt>
		<dd>
			${cacheStatus.rusageSystem }
		</dd>
	</dl>
	<dl>
		<dt>服务器当前存储的items数量:</dt>
		<dd>
			${cacheStatus.currItems }
		</dd>
	</dl>
	<dl>
		<dt>服务器累计存储的items数量:</dt>
		<dd>
			${cacheStatus.totalItems }
		</dd>
	</dl>
	<dl>
		<dt>存储items占用字节数:</dt>
		<dd>
			${cacheStatus.bytes }
		</dd>
	</dl>
	<dl>
		<dt>当前活动连接:</dt>
		<dd>
			${cacheStatus.currConnections }
		</dd>
	</dl>
	<dl>
		<dt>曾经打开过的连接:</dt>
		<dd>
			${cacheStatus.totalConnections }
		</dd>
	</dl>
	<dl>
		<dt>服务器分配的连接构造数:</dt>
		<dd>
			${cacheStatus.connectionStructures }
		</dd>
	</dl>
	<dl>
		<dt>get总请求数:</dt>
		<dd>
			${cacheStatus.cmdGet }
		</dd>
	</dl>
	<dl>
		<dt>set总请求数:</dt>
		<dd>
			${cacheStatus.cmdSet }
		</dd>
	</dl>
	<dl>
		<dt>命中次数:</dt>
		<dd>
			${cacheStatus.getHits }
		</dd>
	</dl>
	<dl>
		<dt>总未命中次数:</dt>
		<dd>
			${cacheStatus.getMisses }
		</dd>
	</dl>
	<dl>
		<dt>命中率:</dt>
		<dd>
			${cacheStatus.hitRate }
		</dd>
	</dl>
	<dl>
		<dt>为获取空闲内存而删除的items数:</dt>
		<dd>
			${cacheStatus.evictions }
		</dd>
	</dl>
	<dl>
		<dt>总读取字节数:</dt>
		<dd>
			${cacheStatus.bytesRead }
		</dd>
	</dl>
	<dl>
		<dt>总发送字节数:</dt>
		<dd>
			${cacheStatus.bytesWritten }
		</dd>
	</dl>
	<dl>
		<dt>分配给memcache的内存:</dt>
		<dd>
			${cacheStatus.limitMaxbytes }
		</dd>
	</dl>
	<dl>
		<dt>当前线程数:</dt>
		<dd>
			${cacheStatus.threads }
		</dd>
	</dl>
	<dl>
		<dt>存储数据数量:</dt>
		<dd>
			${cacheStatus.currItems}
		</dd>
	</dl>
</div>
