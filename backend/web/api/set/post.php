<?
include("../session.php");

if(!isset($_GET['school_id']))
	exit(json_encode(array("successful" => false, "error" => "school_id not provided")));

if(!isset($_GET['contents']))
	exit(json_encode(array("successful" => false, "error" => "contents not provided")));

if(!validSchoolId($_GET['school_id']))
	exit(json_encode(array("successful" => false, "error" => "invalid school id")));

insertPost($user['id'], $_GET['school_id'], $_GET['contents']);
exit(json_encode(array("successful" => true)));

function insertPost($userid, $schoolId, $contents) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("INSERT INTO `anonkhdi_core`.`posts` (`id`, `owner_id`, `school_id`, `date_utc`, `contents`) VALUES (NULL, '".$userid."', '".$schoolId."', '".time()."', '".mysql_real_escape_string($contents)."');") or trigger_error(mysql_error());
}

function validSchoolId($schoolId) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("SELECT * FROM `schools`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($row['id'] == $schoolId)
			return true;
	}
	return false;
}

?>