<?
include("config.php");

if(!isset($_GET['token']))
	exit(json_encode(array("successful" => false, "error" => "no token provided")));

$user = getUser($_GET['token']);
if($user == null)
	exit(json_encode(array("successful" => false, "error" => "invalid token")));
	
function getUser($token) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("SELECT * FROM `users`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($token == $row['token'])
			return array("id" => $row['id'], "email" => $row['email'], "score" => $row['score'], "token" => $row['token']);
	}
	return null;
}

?>