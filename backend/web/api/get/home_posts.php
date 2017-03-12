<?
include("../session.php");

$school = parseSchool($user['email']);
$schoolId = getSchoolId($school);
$posts = getPosts($schoolId);
$posts = appendCommentCount($posts);
$posts = appendVoteCount($posts);

exit(json_encode(array("successful" => true, "posts" => $posts)));

function parseSchool($email) {
	$parts = explode(".", $email);
	$school = $parts[count($parts) - 2];
	if (strpos($school, '@') !== false) {
		$parts = explode("@", $school);
		$school = $parts[1];
	}
	return strtolower($school);
}

function getSchoolId($school) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("SELECT * FROM `schools`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($school == $row['name'])
			return $row['id'];
	}
}

function getPosts($schoolId) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$posts = array();
	$result = mysql_query("SELECT * FROM `posts`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($schoolId == $row['school_id'])
			array_push($posts, array('id' => $row['id'], 'owner_id' => $row['owner_id'], 'school_id' => $row['school_id'], 'date_utc' => $row['date_utc'], 'contents' => $row['contents'], 'comment_count' => 0, 'vote_count' => 0));
	}
	return $posts;
	
}

function appendCommentCount($posts) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("SELECT * FROM  `comments`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		foreach($posts as &$post)
			if($post['id'] == $row['post_id'])
				$post['comment_count']++;
	}
	return $posts;
}

function appendVoteCount($posts) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("SELECT * FROM  `comments`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		foreach($posts as &$post)
			if($row['resource_type'] == "post" && $row['resource_id'] == $post['id'])
				$post['vote_count']++;
	}
	
	return $posts;
}
?>