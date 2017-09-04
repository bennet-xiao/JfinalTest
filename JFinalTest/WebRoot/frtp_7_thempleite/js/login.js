$(function(){  
   $('#login-link').on('click', function(){  
       layer.open({  
           type: 2,  
           title: '用户登录',  
           maxmin: true,  
           skin: 'layui-layer-lan',  
           shadeClose: true, //点击遮罩关闭层  
           area : ['400px' , '280px'],  
           content:'login.html'//弹框显示的url  
       });  
   });  
})