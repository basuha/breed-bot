<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
	<input type="hidden" name="_csrf" value="${_csrf.token}">
	<input type="hidden" name="userId" value="${currentUserId}">
	<input type="hidden" name="username" value="${name}">

	<div class="bg-light mb-2 mx-auto">${name}</div>
	<form action="/logout" method="post">
		<button class="btn btn-primary" type="submit">Log out</button>
		<input type="hidden" name="_csrf" value="${_csrf.token}">
	</form>
	<div class="card-header">
		<span class="badge badge-secondary">
			<h1>
				Breed Bot
			</h1>
		</span>
	</div>

	<div id="scroll" class="mx-auto" style="height:400px;width:inherit;
	overflow:scroll;
	padding-top:100px;padding-bottom:30%; padding-right: 2%; padding-left:2%">
		<div id="getResultDiv" class="mx-auto">
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