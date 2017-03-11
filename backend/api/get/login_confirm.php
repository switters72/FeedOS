<?
include("../config.php");

if(!isset($_GET['email']))
	exit(json_encode(array("successful" => false, "error" => "no email provided")));

$resp = hasVerified($_GET['email']);
if($resp == "-1")
	exit(json_encode(array("successful" => false, "error" => "email not found")));

if($resp == "0")
	exit(json_encode(array("successful" => false, "error" => "not verified")));

exit(json_encode(array("successful" => true)));

function hasVerified($email) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("SELECT * FROM `users`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($email == $row['email']) {
			if($row['email_verified'] == "1")
				return "1";
			else
				return "0";
		}
	}
	return "-1";
}
?>