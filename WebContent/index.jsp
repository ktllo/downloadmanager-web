<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Download Manager</title>
	<script src="js/jquery-3.6.0.min.js"></script>
	<script src="js/jquery-ui.js"></script>
	<link href="js/jquery-ui.css" rel="stylesheet">
	<link href="js/main.css" rel="stylesheet">
	<script>
	function updateUserInfo(){
		$.get( '<%=request.getContextPath()%>/UserInfo' , function(data){
			$('#sysname').text(data.system_name);
			if(data.identified){
				//Handle userinfo box and logout link
				$("#hdr-action").text("Welcome back, "+data.username);
				$("#hdr-action").off('click').on('click',function(){
					$("#hdr-uinfo").toggle();
				});
			}else{
				//Display login link
				$("#hdr-action").text("Login");
				$("#hdr-action").off('click').on('click',function(){
					$("#hdr-login").toggle();
				});
			}
		});
		$.get('<%=request.getContextPath()%>/projects', function(data){
			console.log("There are "+data.projects.length+" projects");
		});
	}
	</script>
</head>
<body>
	<nav>
		Download Manager - <span id="sysname"></span>
		<span class="status" id="hdr-action">Login</span>
		<div class="hidden hdr-info-box ui-corner-all" id="hdr-uinfo">
			<p class="hidden" id="hdr-uinfo-pllit">Last login time : <span id="hdr-uinfo-llit"></span></p>
			<button id="hdr-uinfo-logout">Logout</button>
		</div>
		<div class="hidden hdr-info-box ui-corner-all" id="hdr-login">
			<div class="hidden ui-state-error ui-corner-all" style="padding:0.3em;" id="hdr-login-msg">
				<span class="ui-icon ui-icon-alert"></span>
				<span id="hdr-login-msg-cont"></span>
			</div>
			<table>
				<tr>
					<th><label for="hdr-login-username" style="white-space: nowrap;">Username</label></th>
					<td><input type="text" id="hdr-login-username"></td>
				</tr>
				<tr>
					<th><label for="hdr-login-password">Password</label></th>
					<td><input type="password" id="hdr-login-password"></td>
				</tr>
				<tr>
					<td colspan="2"><button id="hdr-login-submit">Submit</button></td>
				</tr>
			</table>
		</div>
	</nav>
	<main>
		
	</main>
	<script>
	//Perform post-load task
	updateUserInfo();
	$("#hdr-login-submit").button();
	//$("#hdr-action").button();
	$("#hdr-login-submit").click(function(){
		var uname = $("#hdr-login-username").val();
		var pass = $("#hdr-login-password").val();
		if(uname==null || pass==null || uname=="" ||pass==""){
			//Display error message
			$("#hdr-login-msg-cont").text("Username and password are required.");
			$("#hdr-login-msg").show();
			return;
		}
		$.post('<%=request.getContextPath()%>/Login',
				{username: uname, password: pass},
				function(data){
					if(data.status=="failed"){
						$("#hdr-login-msg-cont").text(data.message);
						$("#hdr-login-msg").show();
						$("#hdr-login-password").val("");
					}else{
						updateUserInfo();
						$("#hdr-login-msg").hide();
						$("#hdr-login").hide();
						$("#hdr-login-username").val("");
						$("#hdr-login-password").val("");
						if(data.lastLogin==null){
							$("#hdr-uinfo-pllit").hide();
						}else{
							$("#hdr-uinfo-pllit").show();
							$("#hdr-uinfo-llit").text(data.lastLogin);
						}
					}
				})
	});
	$("#hdr-uinfo-logout").button();
	$("#hdr-uinfo-logout").click(function (){
		$.post('<%=request.getContextPath()%>/Logout', function(data){
			updateUserInfo();
			$("#hdr-uinfo").hide();
			
		});
	});
	</script>
</body>
</html>