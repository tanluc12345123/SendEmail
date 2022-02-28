<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page language="java" import="java.text.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
	<% Format formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
	String from = (String)request.getAttribute("From");
	String to = (String)request.getAttribute("To");
	String cc = (String)request.getAttribute("Cc");
	String subject = (String)request.getAttribute("Subject");
	String sentday = (String)request.getAttribute("Date");
	String message = (String)request.getAttribute("Message");
	//int ID = (int)request.getAttribute("ID");
	String ID = (String)request.getAttribute("Idmail");
	String email = (String)session.getAttribute("email");
	String password = (String)session.getAttribute("password");
	%>
	
        <fieldset>
        
        <!-- Form Name -->
        <legend>Mail</legend>
        
        <!-- Text input-->
        <div class="row mb-3">
		    <label for="inputEmail3" class="col-sm-1 col-form-label" style="margin-left: 600px">FROM:</label>
		    <div class="col-sm-3">
		      <label for="inputEmail1" class="col-form-label"><%=from %></label>
		    </div>
		  	</div>
		  	<div class="row mb-3">
		    <label for="inputPassword3" class="col-sm-1 col-form-label" style="margin-left: 600px">TO:</label>
		    <div class="col-sm-3">
		      <label for="inputEmail1" class="col-form-label"><%=to %></label>
		    </div>
		  </div>
		  <div class="row mb-3">
		    <label for="inputPassword3" class="col-sm-1 col-form-label" style="margin-left: 600px">CC:</label>
		    <div class="col-sm-3">
		      <label for="inputEmail1" class="col-form-label"><%=cc %></label>
		    </div>
		  </div>
		  <div class="row mb-3">
		    <label for="inputPassword3" class="col-sm-1 col-form-label" style="margin-left: 600px">SUBJECT:</label>
		    <div class="col-sm-3">
		      <label for="inputEmail1" class="col-form-label"><%=subject %></label>
		    </div>
		  </div>
		  <div class="row mb-3">
		    <label for="inputPassword3" class="col-sm-1 col-form-label" style="margin-left: 600px">SENT DAY:</label>
		    <div class="col-sm-3">
		      <label for="inputEmail1" class="col-form-label"><%=sentday %></label>
		    </div>
		  </div>
		  <div class="row mb-3">
		    <label for="inputPassword3" class="col-sm-1 col-form-label" style="margin-left: 600px">MESSAGE:</label>
		    <div class="col-sm-3">
		      <label for="inputEmail1" class="col-form-label"><%=message %></label>
		    </div>
		  </div>
        
        
        
        <!-- Button -->
        
          <div class="modal fade" id="ModalReply" tabindex="-1" role="dialog" aria-labelledby="ModalReplyTitle" aria-hidden="true">
					<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
						<h5 class="modal-title" id="ModalReplyTitle">Reply</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						</div>
						<div class="modal-body" style="text-align: left;">
							<form action="ReplyServlet" method="post">
								<input type="hidden" name="email" value="<%=email%>">
								<input type="hidden" name="password" value="<%=password%>">
								<input type="hidden" class="form-control" name="id" value="<%=ID %>">
								<div class="form-group">
								
									<label style="text-align: left;">From:</label> 
									<input type="text" class="form-control" name="from" value="<%=to %>" readonly>
								</div>
								<div class="form-group">
									<label style="text-align: left;">To:</label> 
									<input type="text" class="form-control" name="to" value="<%=from %>" readonly>
								</div>
								<div class="form-group">
									<label style="text-align: left;">Subject:</label> 
									<input type="text" class="form-control" name="subject" placeholder="Enter Subject" value="<%=subject %>" readonly>
								</div>
								<div class="form-group">
									<label for="content" style="text-align: left;">Content:</label>
									<textarea class="form-control" rows="3" name="content"></textarea>
								</div>
								
								<button type="submit" class="btn btn-primary">Send</button>
								<button type="reset" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
							</form>
						</div>
						<div class="modal-footer">
						</div>
          </div>
        </div>
        </div>
        <div class="modal fade" id="ModalForward" tabindex="-1" role="dialog" aria-labelledby="ModalForwardTitle" aria-hidden="true">
					<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
						<h5 class="modal-title" id="ModalForwardTitle">Forward</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						</div>
						<div class="modal-body" style="text-align: left;">
							<form action="ForwardServlet" method="post">
								<input type="hidden" name="email" value="<%=email%>">
								<input type="hidden" name="password" value="<%=password%>">
								<input type="hidden" class="form-control" name="id" value="<%=ID %>">
								<div class="form-group">
								
									<label style="text-align: left;">From:</label> 
									<input type="text" class="form-control" name="from" value="<%=to %>" readonly>
								</div>
								<div class="form-group">
									<label style="text-align: left;">To:</label> 
									<input type="text" class="form-control" name="to" value="">
								</div>
								<div class="form-group">
									<label style="text-align: left;">Subject:</label> 
									<input type="text" class="form-control" name="subject" placeholder="Enter Subject" value="<%=subject %>" readonly>
								</div>
								<div class="form-group">
									<label for="content" style="text-align: left;">Content:</label>
									<textarea class="form-control" rows="3" name="content" readonly><%=message %></textarea>
								</div>
								
								<button type="submit" class="btn btn-primary">Send</button>
								<button type="reset" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
							</form>
						</div>
						<div class="modal-footer">
						</div>
          </div>
        </div>
        </div>
        </fieldset>
        
        <div class="form-group">
          <div style="margin-left: 650px">
			  <button type="button" id="reply" name="reply" class="btn btn-primary" data-toggle="modal" data-target="#ModalReply" data-placement="top" title="Reply">Reply</button>
			  <button type="button" id="forward" name="forward" class="btn btn-primary" data-toggle="modal" data-target="#ModalForward" data-placement="top" title="Forward">Forward</button>
		  </div>
        </div>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
</body>
</html>