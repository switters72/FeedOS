<?

include("../session.php");

exit(json_encode(array("successful" => true, "posts" => getHistory($user['id']))));

function getHistory($userid) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$posts = array();
	$result = mysql_query("SELECT * FROM `posts`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($userid == $row['owner_id'])
			array_push($posts, array('id' => $row['id'], 'owner_id' => $row['owner_id'], 'school_id' => $row['school_id'], 'date_utc' => $row['date_utc'], 'contents' => $row['contents'], 'comment_count' => "0", "vote_count" => "0"));
	}
	return $posts;
}

?>