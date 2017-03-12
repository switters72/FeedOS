<?
include("../session.php");

if(!isset($_GET['post_id']))
	exit(json_encode(array("successful" => false, "error" => "post_id not provided")));

if(!postIdIsValid($_GET['post_id']))
	exit(json_encode(array("successful" => false, "error" => "post_id is not valid")));

$comments = getComments($_GET['post_id']);
$comments = appendVoteCount($comments);

exit(json_encode(array("successful" => true, "comments" => $comments)));

function postIdIsValid($postId) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("SELECT * FROM  `posts`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($row['id'] == $postId)
			return true;
	}
	return false;
}

function getComments($postId) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$comments = array();
	$result = mysql_query("SELECT * FROM  `comments`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($row['post_id'] == $postId)
			array_push($comments, array("id" => $row['id'], "post_id" => $row['post_id'], "date_utc" => $row['date_utc'], "contents" => $row['contents'], 'vote_count' => 0));
	}
	return $comments;
}

function appendVoteCount($comments) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("SELECT * FROM  `votes`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		foreach($comments as &$comment)
			if($row['resource_type'] == "comment" && $row['resource_id'] == $comment['id'])
				$comment['vote_count']++;
	}
	
	return $comments;
}

?>