<#import "parts/common.ftl" as c>

<@c.page>
	<div class="container">
		<select>
			<option>qweqwrrwq</option>
			<option>qweqwrrwq</option>
			<option>qweqwrrwq</option>
			<option>qweqwrrwq</option>
			<option>qweqwrrwq</option>
			<option>qweqwrrwq</option>
		</select>

		<h3 style="color:#0000ff">POST-GET AJAX Example</h3>

		<div>
			<form class="form-inline" style="margin:20px 20px 20px 20px" id="customerForm">
				<div class="form-group">
					<input type="text" class="form-control" id="message" placeholder="Написать сообщение..."/>
				</div>
				<button type="submit" class="btn btn-default" style="margin-left:20px; margin-right:5px">Submit</button>
			</form>
		</div>

		<div class="col-sm-7" id="postResultDiv">
		</div>

		<div class="col-sm-7" style="margin:20px 0px 20px 0px">
			<button id="getAllCustomerId" type="button" class="btn btn-primary">Get All Customers</button>
			<div id="getResultDiv" style="padding:20px 10px 20px 50px">
				<ul class="list-group">
				</ul>
			</div>
		</div>

	</div>
</@c.page>