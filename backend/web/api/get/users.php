<?
include("../config.php");

if(!isset($_GET['token']))
	exit(json_encode(array('successful' => false, 'error' => "token not set")));

$user = getUser($_GET['token']);
if($user == null)
	exit(json_encode(array('successful' => false, 'token' => $_GET['token'], 'error' => "invalid token")));

exit(json_encode(array('successful' => true, 'user' => $user)));

function getUser($token) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);

	$result = mysql_query("SELECT * FROM `users`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if(strtolower($token) == strtolower($row['token']))
			return array("id" => (int)$row['id'], "email" => $row['email'], "score" => (int)$row['score'], "token" => $row['token']);
	}
	return null;
}

?>