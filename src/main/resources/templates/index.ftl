<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
	<input type="hidden" name="_csrf" value="${_csrf.token}">
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
	<div class=form-group">
		<form id="customerForm">
			<div>
				<input class="form-control" type="text" id="message" placeholder="Написать сообщение..."/>
				<input type="hidden" name="_csrf" value="${_csrf.token}">
			</div>
			<button type="submit" class="btn btn-primary" style="margin-left:20px; margin-right:5px">Отправить</button>
		</form>
	</div>

<#--		<div class="col-sm-7" id="postResultDiv">-->
<#--		</div>-->

	<div class="col-sm-7" style="margin:20px 0px 20px 0px">
		<div id="getResultDiv" style="padding:20px 10px 20px 50px">
			<ul class="list-group">
			</ul>
		</div>
	</div>
</@c.page>