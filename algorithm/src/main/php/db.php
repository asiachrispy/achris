<?php
$con = mysql_connect("192.168.10.11:3316", "recruit", "uc1q2w3e4r");
if (!$con) {
    die('Could not connect: ' . mysql_error());
}
mysql_select_db("DB_SEARCH", $con);
$insert_sql = "INSERT INTO tb_st_click_log (uid,block_id,jid,product_date) VALUES";
#mysql_query($insert_sql);

mysql_close($con);
?>