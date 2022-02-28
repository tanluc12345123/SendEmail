<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page language="java" import="java.util.*" %>
    <%@ page language="java" import="receive.Receive" %>
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
<script>
            function toggle(source) {
                checkboxes = document.getElementsByName('product');
                for (var i = 0, n = checkboxes.length; i < n; i++) {
                    checkboxes[i].checked = source.checked;
                }
            }
        </script>
</head>
<body>
<%String email = (String)session.getAttribute("email");
String password = (String)session.getAttribute("password");
%>
	
    
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-8"><h2>EMail <b>Details</b></h2></div>
                    <div class="col-sm-4">
                        <button type="button" class="btn btn-info add-new" data-toggle="modal" data-target="#ModalSendEmail" data-placement="top" title="SendEmail"><i class="fa fa-plus"></i> Send email</button>
                    </div>
                    <div class="modal fade" id="ModalSendEmail" tabindex="-1" role="dialog" aria-labelledby="ModalSendEmailTitle" aria-hidden="true">
					<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
						<h5 class="modal-title" id="ModalSendEmailTitle">Send email</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						</div>
						<div class="modal-body" style="text-align: left;">
						<form action="SendMailServlet" method="post" enctype="multipart/form-data">
							<input type="hidden" name="email" value="<%=email%>">
							<input type="hidden" name="password" value="<%=password%>">
							<div class="form-group">
								<label style="text-align: left;">To:</label> 
								<input type="text" class="form-control" name="to" placeholder="Enter Email">
							</div>
							<div class="form-group">
								<label style="text-align: left;">Subject:</label> 
								<input type="text" class="form-control" name="subject" placeholder="Enter Subject">
							</div>
							<div class="form-group">
								<label for="content" style="text-align: left;">Content:</label>
								<textarea class="form-control" rows="3" name="content"></textarea>
							</div>
							<div class="form-group">
							    <label for="exampleFormControlFile1">Example file input</label>
							    <input type="file" class="form-control-file" name="file" id="exampleFormControlFile1">
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
                </div>
            </div>
            <form method="post" action="DeleteMailsServlet">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th><input type="checkbox" id="checkBoxAll" onClick="toggle(this)" /></th>
                        <th>From</th>
                        <th>Subject</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <% Format formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                
                ArrayList<Receive> rc = (ArrayList<Receive>)request.getAttribute("ReceiveList");
						for(int i=0;i<rc.size();i++){
					%>
                    <tr>
                        <td><input type="checkbox" name="product" value="<%=i%>"></td>
                        <td><%=rc.get(i).getFrom() %></td>
                        <td><a href="ReadMail?ID=<%=i%>"><%=rc.get(i).getSubject() %></a></td>
                        <td><%=formatter.format(rc.get(i).getSentday())%></td>
                        <td>
                            <a href="DeleteMailServlet?ID=<%=i%>" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>
                        </td>
                    </tr>
                    <%} %>
                </tbody>
            </table>
            <input type="submit" class="btn btn-primary" value="Delete" id="btDelete" onclick="return confirm('Are you sure delete?')"/>
            </form>
        </div>
     
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
</body>
</html>