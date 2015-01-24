<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>代办列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css" rel="stylesheet"
	href="css/myWorkTerrace/waitList.css" />
<jsp:include page="../../comm/comm_easyUI_js.jsp" />
<jsp:include page="../../comm/comm_WdatePicker.js_js.jsp" />

<script type="text/javascript">
	function xiao(url) {
		$("#grid")
				.datagrid(
						{
							url : url,
							rownumbers : true,
							title : '代办事项',
							iconCls : 'icon-save',
							pagination : true,//显示底部分页栏
							pageSize : 10,//默认每页记录数，pagination参数为true时才有效
							pageList : [ 5, 10, 15 ], //显示列表记录数的下拉框选项，pagination参数为true时才有效
							//fitColumns : true,//自适应宽度，防止水平滚动
							striped : true,//隔行变色
							singleSelect : true,
							selectOnCheck : false,
							checkOnSelect : false,
							width : 750,
							toolbar : [
									{
										id : "cha",
										text : '查看',
										iconCls : 'icon-search',
										handler : function() {
											var row = $("#grid").datagrid("getSelected"); //获得id
										}
									},
									'-',
									{
										id : "chu",
										text : '去处理',
										iconCls : 'icon-edit',
										handler : function() {
											var row = $("#grid").datagrid(
													'getSelected');
											if (row == null) {
												alert("请选择要查看的对象");
											} else {
												location.href = 'getBeHeTaskById.action?beHeTask.task_id='
														+ row.task_id;
											}

										}
									},
									'-',
									{
										id : "Shen",
										text : '提交审核',
										iconCls : 'icon-ok',
										handler : function() {
											var row = $("#grid").datagrid(
													'getSelected');
											if (row == null) {
												alert("请选择要审查的对象");
											} else {
												location.href = 'getBeHeTaskByIdForShen.action?beHeTask.task_id='
														+ row.task_id;
											}

										}
									}, ],
							columns : [ [ {
								field : 'task_type',
								title : '待办任务类型',
								text : '消缺任务',
								align : 'center',
								width : 150
							}, {
								field : 'task_name',
								title : '待办任务名称',
								align : 'left',
								width : 260
							}, {
								field : 'from_time',
								title : '到达时间',
								align : 'center',
								width : 150,
								formatter : function(data) {
									return data.substring(0, 10);
								}
							}, {
								field : 'from_user_name',
								title : '下达人',
								align : 'center',
								width : 150,
							}, ] ],
							onClickCell : function(index, field, v) {
								if (field == "opr") {
									var id = $(this).datagrid("getRows")[index].id; //获得id
									alert(id);
									/* window.location.href = "getItem.action?sid="
											+ id; */
								}
							}
						});
	}
</script>
</head>

<body>
	<s:iterator value="#session.roleLimit" var="role">
		<s:if test="role_id=='role1004'">
			<script type="text/javascript">
				$(function() {
					xiao('byXiao.action');
				});
			</script>
		</s:if>
		<s:if test="role_id=='role1003'">
			<script type="text/javascript">
				$(function() {
					xiao('byXun.action');
				});
			</script>
		</s:if>
		<s:if test="role_id=='role1002'">
			<script type="text/javascript">
				$(function() {
					xiao('byXian.action');
				});
			</script>
		</s:if>
		<s:if test="role_id=='role1001'">
			<script type="text/javascript">
				$(function() {
					xiao('byGuan.action');
				});
			</script>
		</s:if>
	</s:iterator>


	<table id="grid"></table>
</body>
</html>
