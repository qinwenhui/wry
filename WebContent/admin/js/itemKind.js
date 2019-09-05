/**
 * 
 */
$(function(){
	//定义获取物品类型列表的函数
	function getItemKindList(){
		$.ajax({
			url: wryConfig.SERVER_URL+'/api/kind/queryKind.action',
			type: 'get',
			dataType: 'json',
			success: function(data){
				console.log(data)
				fillList(data.data)
			},
			error: function(data){
				console.log(data)
			}
		})
	}
	getItemKindList();
	/*
	 * 定义填充列表的函数
	 */
	var itemLength = 0;
	function fillList(data){
		var tbody = $('#tbody')
		var html = "";
		for(var i=0;i<data.length;i++){
			itemLength++
			var kind = data[i];
			html += '<tr><td>'+kind.id+'</td><td>'+kind.name+'</td><td>'
			+kind.description+'</td><td>￥'+kind.price+'</td><td><button type="button" class="btn btn-default" id="updateBtn'
			+i+'" value="'+kind.id+'">修改</button><button type="button" class="btn btn-default" style="margin-left: 10px;"  value="'
			+kind.id+'" id="deleteBtn'+i+'">删除</button></td></tr>';
		}
		tbody.html(html);
		setUpdateBtnClick()
		setDeleteBtnClick()
	}
	//
	var addBtn = $('#addBtn');
	addBtn.click(function(){
		var addDiv = $('#add-div');
		addDiv.fadeIn(200);
	});
	var selectDeleteBtn = $("#selectDeleteBtn");
	selectDeleteBtn.click(function(){
		var deleteDiv = $('#delete-div');
		deleteDiv.fadeIn(200);
	});
	//取消按钮三连
	$("#cancelBtn").click(function(){
		$("#update-div").fadeOut(200);
	})
	$("#cancelBtn1").click(function(){
		$("#add-div").fadeOut(200);
	})
	$("#cancelBtn2").click(function(){
		$("#delete-div").fadeOut(200);
	})
	//确认按钮三连
	$("#updateSubmit").click(function(){
		var kindName = $("#updateKindName").val()
		var kindDecoration = $("#updateKindDecoration").val()
		var kindPrice = $("#updateKindPrice").val()
		if(kindName==""){
			alert("名称不能为空")
			return false
		}
		if(kindDecoration==""){
			alert("描述不能为空")
			return false
		}
		if(kindPrice==""){
			alert("价格不能为空")
			return false
		}
		//进行修改的网络请求
		updateAjax()
	})
	$("#addSubmit").click(function(){
		var kindName = $("#addKindName").val()
		var kindDecoration = $("#addKindDecoration").val()
		var kindPrice = $("#addKindPrice").val()
		if(kindName==""){
			alert("名称不能为空")
			return false
		}
		if(kindDecoration==""){
			alert("描述不能为空")
			return false
		}
		if(kindPrice==""){
			alert("价格不能为空")
			return false
		}
		//进行修改的网络请求
		addAjax()
	})
	$("#selectDeleteSubmit").click(function(){
		var kindId = $("#inputIdToChose").val()
		if(kindId==""){
			alert("id不能为空，爬")
			return false
		}
		//进行修改的网络请求
		selectDeleteAjax()
	})
	
	
	
	function setUpdateBtnClick(){
		for(var i=0;i<itemLength;i++){
			$("#updateBtn"+i).click(function() {
				$('#update-div').fadeIn(200);
				$("#updateKindId").val($(this).val())
			})
		}
	}
	function setDeleteBtnClick(){
		for(var i=0;i<itemLength;i++){
			$("#deleteBtn"+i).click(function() {
				var id = $(this).val();
				deleteAjax(id);
			})
		}
	}
	
	
	function updateAjax(){
		$.ajax({
			url:wryConfig.SERVER_URL+"/api/kind/updateKind.action",
			type:"post",
			data:'name='+$("#updateKindName").val()+'&description='+$("#updateKindDecoration").val()
			+'&price='+$("#updateKindPrice").val()+'&id='+$("#updateKindId").val()+'',
			dataType:"json",
			success:function(data){
				console.log(data)
				if(data.code==0){
					alert(data.msg)
					getItemKindList()
					$("#update-div").fadeOut(200);
				}
			},
			error:function(data){
				console.log(data)
			}
		})
	}
	function addAjax(){
		$.ajax({
			url:wryConfig.SERVER_URL+"/api/kind/addKind.action",
			type:"post",
			data:'name='+$("#addKindName").val()+'&description='+$("#addKindDecoration").val()
			+'&price='+$("#addKindPrice").val()+'&id='+$("#addKindId").val()+'',
			dataType:"json",
			success:function(data){
				console.log(data)
				if(data.code==0){
					alert(data.msg)
					getItemKindList()
					$("#add-div").fadeOut(200);
				}else{
					alert("输入格式有误！")
				}
			},
			error:function(data){
				console.log(data)
			}
		})
	}
	function deleteAjax(id){
		$.ajax({
			url:wryConfig.SERVER_URL+"/api/kind/deleteKind.action?id="+id,
			type:"get",
			dataType:"json",
			success:function(data){
				console.log(data)
				if(data.code==0){
					alert(data.msg)
					getItemKindList()
					$("#delete-div").fadeOut(200);
				}
			},
			error:function(data){
				console.log(data)
				alert(data.msg)
			}
		})
	}
	function selectDeleteAjax(){
		$.ajax({
			url:wryConfig.SERVER_URL+"/api/kind/deleteKind.action",
			type:"get",
			data:'id='+$("#inputIdToChose").val()+'',
			dataType:"json",
			success:function(data){
				console.log(data)
				if(data.code==0){
					alert(data.msg)
					getItemKindList()
					$("#delete-div").fadeOut(200);
				}
			},
			error:function(data){
				console.log(data)
				alert("删除失败")
			}
		})
	}
	//js能够调用的方法,使用$.fn修饰
	$.fn.searchKind=function (){
		var a = {
				url:wryConfig.SERVER_URL+"/api/kind/queryKindByKeyword.action?keyword="+$("#keyword").val(),
				type:"get",
				dataType:"json",
				success:function(data){
					console.log(data)
					if(data.code==0){
						fillList(data.data)
					}else{
						alert(data.msg)
					}
				},
				error:function(data){
					console.log(data)
					
				}
			}
		$.ajax(a)
	}
	
})
function searchKind(){
	//js调用jquery的方法，用$().fun();
	$().searchKind()
	return false;
}