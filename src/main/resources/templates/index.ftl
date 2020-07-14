<#import "parts/common.ftl" as c>

<@c.page>
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