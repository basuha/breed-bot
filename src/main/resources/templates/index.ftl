<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
	<input type="hidden" name="_csrf" value="${_csrf.token}">
	<input type="hidden" name="userId" value="${currentUserId}">
	<input type="hidden" name="username" value="${name}">

	<div class="navbar-text mr-3 bg-warning">${name}</div>
	<form action="/logout" method="post">
		<button class="btn btn-primary" type="submit">Выйти</button>
		<input type="hidden" name="_csrf" value="${_csrf.token}">
	</form>
	<div class="card-header">
		<span class="badge badge-secondary">
			<h1>
				Breed Bot
			</h1>
		</span>
	</div>


<#--		<div class="col-sm-7" id="postResultDiv">-->
<#--		</div>-->
	<div id="scroll" class="mx-auto" style="height:600px;width:1100px;overflow:auto;padding-top:100px;padding-bottom:10px; padding-right: 0">
	<div style="margin:20px 0px 20px 0px">
		<div id="getResultDiv">
			<ul class="list-group">
			</ul>
		</div>
	</div>

	<form id="messageForm">
		<div class="input-group mb-3">
			<input type="text" id="message" class="form-control" placeholder="Message here...">
			<div class="input-group-append">
				<button class="btn btn-outline-primary" type="submit">Send</button>
			</div>
		</div>
	</form>
</@c.page>