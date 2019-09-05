/**
 * Created by qwh on 2019/5/23.
 */
$(function(){
	//当前页有多少条数据在显示
	var currentNumber = 0;

	getAllVersionList();
    
    //设置监听
    setOnClick();
    /*
    	获取列表
     */
    function getAllVersionList(){
        $.ajax({
            url: wryConfig.SERVER_URL+'/api/version/queryAllVersion.action',
            type: 'get',
            dataType: 'json',
            success: function(data){
            	if(data.code == 0){
            		//填充界面列表
            		fillList(data.data)
            	}else{
            		alert(data.msg)
            	}
                console.log(data);
            },
            error: function(data){
                console.log("请求出错"+data);
            }
        })
    }
    /*
     * 填充列表的函数
     */
    function fillList(data){
    	var trDiv = $('#tr-div')
    	var html = "";
    	for(var i=0;i<data.length;i++){
    		currentNumber++;
    		html += '<tr><td><div class="checkbox"><label><input type="checkbox" id="checkbox'+i+'" value="'
    		+data[i].id+'" aria-label="..."></label></div><td>'+data[i].id+'</td><td>'+data[i].appName+'</td><td>'
    		+data[i].versionCode+'</td><td>'+data[i].versionName+'</td><td>'+data[i].content+'</td><td>'
    		+format(data[i].publishTime)+'</td><td><a href="'+data[i].download+'">'
    		+data[i].download+'</a></td><td><button type="button" class="btn btn-default" id="updateBtn'
    		+i+'" value="'+data[i].id+'">修改</button></td></tr>'
    	}
    	trDiv.html(html);
    	//设置修改的按钮的监听，在这里设置是因为需要列表先刷新出来才能进行删除按钮的监听
    	updateBtnOnClick();
    }
    /*
     * 设置监听的函数
     */
    function setOnClick(){
    	//点击全部显示的按钮
    	$('#allVersionBtn').click(function(){
    		getAllVersionList();
    	})
    	//点击模糊查询的按钮
    	$('#searchBtn').click(function(){
    		//获取查询的关键字
    		var keyword = $('#keyword').val();
    		//进行模糊查询
    		$.ajax({
                url: wryConfig.SERVER_URL+'/api/version/queryVersionByKeyword.action?keyword='+keyword,
                type: 'get',
                dataType: 'json',
                success: function(data){
                	if(data.code == 0){
                		//填充界面列表
                		fillList(data.data);
                	}else{
                		alert(data.msg);
                	}
                    console.log(data);
                },
                error: function(data){
                    console.log(data);
                }
            })
    	});
    	//点击添加按钮
    	$('#addVersionBtn').click(function (){
    		//弹出添加框
    		$('#addVersion-div').show(200);
    	});
    	//点击取消添加按钮
    	$('#cancelAddBtn').click(function (){
    		//隐藏添加框
    		$('#addVersion-div').hide(200);
    	});
    	//点击取消修改按钮
    	$('#cancelUpdateBtn').click(function (){
    		//弹出添加框
    		$('#updateVersion-div').hide(200);
    	});
    	//点击确认提交
    	$('#addVersionSubmit').click(function (){
    		var appName = $('#appName').val();
    		var versionName = $('#versionName').val();
    		var versionCode = $('#versionCode').val();
    		var versionDownload = $('#versionDownload').val();
    		var versionContent = $('#versionContent').val();
    		if(appName==''||versionName==''||versionCode==''||versionDownload==''||versionContent==''){
    			alert('所需内容不能为空,请完善')
    			return false;
    		}
    		//执行添加的网络请求
    		addVersion($('#addVersionForm').serialize());
    	});
    	//点击确认修改提交按钮
    	$('#updateVersionSubmit').click(function(){
    		var appName = $('#updateAppName').val();
    		var versionName = $('#updateVersionName').val();
    		var versionCode = $('#updateVersionCode').val();
    		var versionDownload = $('#updateVersionDownload').val();
    		var versionContent = $('#updateVersionContent').val();
    		if(appName==''||versionName==''||versionCode==''||versionDownload==''||versionContent==''){
    			alert('所需内容不能为空,请完善')
    			return false;
    		}
    		//执行修改的网络请求
    		updateVersion($('#updateVersionForm').serialize());
    	})
    	//点击批量删除
    	$('#selectDeleteBtn').click(function (){
    		if(confirm("确定要删除吗?"))
    	     {
    			var flag = false;
    			for(var i=0;i<currentNumber;i++){
    	    		//如果当前选择框处于选择状态
    	    		if($('#checkbox'+i).is(':checked')){
    	    			flag = true;
    	    		}
    	    	}
    			if(flag){
    				//执行删除所选的函数
        			deleteSelect();
    			}else{
    				alert("至少选择一项删除");
    			}
    			return true;
    	     }else{
    		   return false;
    		 }
    	});
    	
    	//点击空白处隐藏详情框
    	$(document).click(function(){
    		$("#versionInfoDiv").hide(200);
    	})
    	//点击提示框不隐藏（阻止冒泡事件）
    	$("#versionInfoDiv").click(function(event){
    		event.stopPropagation();
    	});
    }
    
    /*
     * 选择删除
     */
    function deleteSelect(){
    	//循环当前数据的选择框
    	for(var i=0;i<currentNumber;i++){
    		//如果当前选择框处于选择状态
    		if($('#checkbox'+i).is(':checked')){
    			//执行删除操作
    			var id = $('#checkbox'+i).val();
    			deleteOne(id);
    		}
    	}
    }
    
    /*
     * 删除某一项
     */
    function deleteOne(id){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/version/deleteVersionById.action?id='+id,
            type: 'get',
            dataType: 'json',
            success: function(data){
            	if(data.code == 0){
            		//删除成功，重新获取列表
            		getAllItemList();
            	}else{
            		alert(data.msg)
            	}
                console.log(data);
            },
            error: function(data){
                console.log(data);
            }
        });
    }
    
    /*
     * 修改按钮的监听
     */
    function updateBtnOnClick(){
    	//点击修改按钮,先循环所有的修改按钮
    	for(var i=0;i<currentNumber;i++){
    		$('#updateBtn'+i).click(function(){
    			//获取版本的id
    			var id = $(this).val();
    			//获取该版本的详细信息
    			getVersionInfo(id);
    		});
    	}
    }
    
    /*
     * 获取某个版本的详细信息
     */
    function getVersionInfo(id){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/version/queryVersionById.action?id='+id,
            type: 'get',
            dataType: 'json',
            success: function(data){
            	if(data.code == 0){
            		//获取详情成功，填充到修改的提示框
            		$('#updateAppName').val(data.data.appName);
            		$('#updateVersionName').val(data.data.versionName);
            		$('#updateVersionCode').val(data.data.versionCode);
            		$('#updateVersionDownload').val(data.data.download);
            		$('#updateVersionContent').val(data.data.content);
            		$('#updateVersionId').val(data.data.id);
            		//显示修改框
            		$('#updateVersion-div').show(200);
            	}else{
            		alert(data.msg)
            	}
                console.log(data);
            },
            error: function(data){
                console.log("获取某个版本失败"+data);
            }
        });
    }

    /*
     * 添加版本的网络请求
     */
    function addVersion(postData){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/version/addVersion.action',
            type: 'post',
            dataType: 'json',
            data: postData,
            success: function(data){
            	if(data.code == 0){
            		//添加成功，重新获取列表
            		getAllItemList();
            		alert(data.msg)
            		$('#addVersion-div').hide(200);
            	}else{
            		alert(data.msg)
            	}
                console.log(data);
            },
            error: function(data){
                console.log(data);
            }
        });
    }
    
    /*
     * 修改版本的网络请求
     */
    function updateVersion(postData){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/version/updateVersionById.action',
            type: 'post',
            dataType: 'json',
            data: postData,
            success: function(data){
            	if(data.code == 0){
            		//修改成功，重新获取列表
            		getAllItemList();
            		alert(data.msg)
            		$('#updateVersion-div').hide(200);
            	}else{
            		alert(data.msg)
            	}
                console.log(data);
            },
            error: function(data){
                console.log(data);
            }
        });
    }
   
    /*
     * 时间戳转时间
     */
    function add0(m){return m<10?'0'+m:m }
    function format(shijianchuo)
    {
	    //shijianchuo是整数，否则要parseInt转换
	    var time = new Date(shijianchuo);
	    var y = time.getFullYear();
	    var m = time.getMonth()+1;
	    var d = time.getDate();
	    var h = time.getHours();
	    var mm = time.getMinutes();
	    var s = time.getSeconds();
	    return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    }
})