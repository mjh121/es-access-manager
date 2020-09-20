<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.ssu.spec.repository.*" %>
<%@ page import="java.util.ArrayList" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<title>Specification Upload page</title>

	<link rel="StyleSheet" href="css/dtree.css" type="text/css" />
	<script type="text/javascript" src="js/dtree.js"></script>

</head>

<body>

<%
	FileAccessor fileAccessor = new FileAccessor(RepositConstants.SPEC_FILE_PATH);
	ArrayList<String> fileList = fileAccessor.getFileList(RepositConstants.ALL_TYPE);
	ArrayList<String> tmpFileList = null;
	int count = fileList.size()+1;
%>

<h1>Specification Upload page</h1>

<div id="container" style="float:left width:auto">

	<div class="dtree" style>
	<h2>xml file view</h2>
	
		<p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>
		
		<script type="text/javascript">
			
			d = new dTree('d');
	
			d.add(0,-1,'documents');
			<%
			for(int i=0; i<fileList.size(); i++) {
				tmpFileList = fileAccessor.getFileList(fileList.get(i), RepositConstants.ALL_TYPE);
			%>
				
				d.add(<%=i+1%>,0,'<%=fileList.get(i)%>','tempPage.html');
				<%
				for(int j=0; j<tmpFileList.size(); j++) {
					count = count+1;
				%>
					d.add(<%=count%>,<%=i+1%>,'<%=tmpFileList.get(j)%>','tempPage.html');
				<%
				}
			}
			%>
			document.write(d);
		</script>
	
	</div>
	<div id = "eventbox" style="float:left">
		<h2>Upload </h2>
		<form action="fileUpload" id="fileUpload" name = "fileUpload" method="post" enctype="multipart/form-data">
			<div> <b>moduleList : </b><select id="moduleList" name="moduleList">
				<%
				for(int i=0; i < fileList.size(); i++) {
				%>
					<option value="<%=fileList.get(i)%>"><%=fileList.get(i)%></option> 
				<%		
				}	
				%>
				</select> <br><br>			
			</div>
			<div>
				file select : <input type="file" id="uploadFile" name="uploadFile"><br><br>
				<input type="submit" name="upload" value="send">
			</div>
		</form>
	</div>
</div>

</body>

</html>