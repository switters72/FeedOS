<?
include("../session.php");

if(!isset($_GET['post_id']))
	exit(json_encode(array("successful" => false, "error" => "post_id not provided")));

if(!isset($_GET['contents']))
	exit(json_encode(array("successful" => false, "error" => "contents not provided")));

if(!validPostId($_GET['post_id']))
	exit(json_encode(array("successful" => false, "error" => "invalid post_id")));

insertComment($user['id'], $_GET['post_id'], $_GET['contents']);
exit(json_encode(array("successful" => true)));

function insertComment($userid, $postId, $contents) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("INSERT INTO `anonkhdi_core`.`comments` (`id`, `post_id`, `owner_id`, `date_utc`, `contents`) VALUES (NULL, '".$postId."', '".$userid."', '".time()."', '".mysql_real_escape_string($contents)."');") or trigger_error(mysql_error());
}

function validPostId($postId) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("SELECT * FROM `posts`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($row['id'] == $postId)
			return true;
	}
	return false;
}

?>