/**
 * Created by qwh on 2019/5/23.
 */
$(function(){
	//当前页有多少条数据在显示
	var currentNumber = 0;

    getAllItemList();
    
    //设置监听
    setOnClick();
    /*
    	获取列表
     */
    function getAllItemList(){
        $.ajax({
            url: wryConfig.SERVER_URL+'/api/trustOrder/queryAllTrustOrderVo.action',
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
    	//订单状态
    	var state = '已取消';
    	for(var i=0;i<data.length;i++){
    		if(data[i].state == 0){
    			state = '正在托管';
    		}
    		if(data[i].state == 1){
    			state = '结束托管';
    		}
    		if(data[i].state == 2){
    			state = '未支付';
    		}
    		if(data[i].state == 3){
    			state = '已取消';
    		}
    		currentNumber++;
    		html += '<tr><td><div class="checkbox"><label><input type="checkbox" id="checkbox'+i+'" value="'+data[i].id+'" aria-label="..."></label></div></td><td>'+data[i].id+'</td><td>'+data[i].item.name+'</td><td><img src="'+wryConfig.SERVER_URL+data[i].item.image+'" width="50px" height="50px"></td><td>'+data[i].item.notes+'</td><td>'+data[i].trustDuration+'小时</td><td>'+data[i].user.username+'</td><td>'+data[i].waiter.nickname+'</td><td>￥'+data[i].price+'</td><td>'+state+'</td><td>'+format(data[i].transactionTime)+'</td><td><button type="button" class="btn btn-default" id="InfoBtn'+i+'" value="'+data[i].id+'">详情</button>&nbsp;&nbsp;<button type="button" class="btn btn-default" id="deleteBtn'+i+'" value="'+data[i].id+'">删除</button></td></tr>'
    	}
    	trDiv.html(html);
    	//设置删除和详情的按钮的监听，在这里设置是因为需要列表先刷新出来才能进行删除按钮的监听
    	deleteBtnOnClick();
    	infoBtnOnClick();
    }
    /*
     * 设置监听的函数
     */
    function setOnClick(){
    	//全部通知的按钮
    	$('#allNoticeBtn').click(function(){
    		//再次获取全部通知填充列表
    		getAllItemList();
    	});
    	//点击模糊查询的按钮
    	$('#seachBtn').click(function(){
    		//获取查询的金额区间
    		var minPrice = $('#minPrice').val();
    		var maxPrice = $('#maxPrice').val();
    		//进行模糊查询
    		$.ajax({
                url: wryConfig.SERVER_URL+'/api/trustOrder/queryTrustOrderVoByPrice.action?minPrice='+minPrice+'&maxPrice='+maxPrice,
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
    	//点击正在托管按钮
    	$('#currentTrustBtn').click(function (){
    		getOrderByState(0);
    	});
    	//点击结束托管按钮
    	$('#overTrustBtn').click(function (){
    		getOrderByState(1);
    	});
    	//点击未支付按钮
    	$('#noPayBtn').click(function (){
    		getOrderByState(2);
    	});
    	//点击已取消按钮
    	$('#cancelTrustBtn').click(function (){
    		getOrderByState(3);
    	});
    	
    	//点击批量删除
    	$('#selectDeleteBtn').click(function (){
    		if(confirm("确定要删除吗?"))
    	     {
    			//执行删除所选的函数
    			deleteSelect();
    			return true;
    	     }else{
    		   return false;
    		 }
    	});
    	
    	//点击空白处隐藏详情框
    	$(document).click(function(){
    		$("#orderInfo-div").hide(200);
    	})
    	//点击提示框不隐藏（阻止冒泡事件）
    	$("#orderInfo-div").click(function(event){
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
            url: wryConfig.SERVER_URL+'/api/trustOrder/deleteTrustOrder.action?id='+id,
            type: 'get',
            dataType: 'json',
            success: function(data){
            	if(data.code == 0){
            		//删除成功，重新获取物品列表
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
     * 删除按钮的监听
     */
    function deleteBtnOnClick(){
    	//点击删除按钮,先循环所有的删除按钮
    	for(var i=0;i<currentNumber;i++){
    		$('#deleteBtn'+i).click(function(){
    			//获取这一个订单的id
    			var id = $(this).val();
    			if(confirm("是否删除")){
    				deleteOne(id);
    				return true;
    			}else{
    				return false;
    			}
    			
    		});
    	}
    }
    /*
     * 根据订单状态查询订单列表
     */
    function getOrderByState(query_state){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/trustOrder/queryTrustOrderVoByState.action?state='+query_state,
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
    * 查询某一个订单信息的详情
    */
    function infoBtnOnClick(){
    	for(var i=0;i<currentNumber;i++){
    		$('#InfoBtn'+i).click(function(){
    			//获取id
    			var id = $(this).val();
    			$.ajax({
    	            url: wryConfig.SERVER_URL+'/api/trustOrder/queryTrustOrderVoById.action?id='+id,
    	            type: 'get',
    	            dataType: 'json',
    	            success: function(data){
    	            	if(data.code == 0){
    	            		//获取详情成功，填充到详情的提示框
    	            		fillOrderInfo(data.data);
    	            		//显示详情框
    	            		$('#orderInfo-div').show(200);
    	            	}else{
    	            		alert(data.msg)
    	            	}
    	                console.log(data);
    	            },
    	            error: function(data){
    	                console.log("获取某个评论信息失败"+data);
    	            }
    	        });
    		});
    	}
    }
   
    /*
     * 填充详情界面
     */
    function fillOrderInfo(order){
    	$('#order_id').text(order.id);
    	$('#order_item_name').text(order.item.name);
    	$('#order_user_address').text(order.user.address);
    	$('#order_trustDuration').text(order.trustDuration+"小时");
    	$('#order_user_id').text(order.user.id);
    	$('#order_user_username').text(order.user.username);
    	$('#order_waiter_id').text(order.waiter.id);
    	$('#order_waiter_nickname').text(order.waiter.nickname);
    	$('#order_price').text("￥"+order.price);
    	$('#order_transactionTime').text(format(order.transactionTime));
    	$('#order_satisfaction').text(order.satisfaction);
    	$('#order_notes').text(order.item.notes);
    	$('#order_item_image').attr('src',wryConfig.SERVER_URL+order.item.image);
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