<?php
require "connection.php";
$sql_delete="ALTER TABLE users DROP id; ";
$sql_urutan="ALTER TABLE users ADD  id INT( 255 ) NOT NULL AUTO_INCREMENT FIRST ,ADD PRIMARY KEY (id);";
mysqli_query($con,$sql_delete);
mysqli_query($con,$sql_urutan);
?>