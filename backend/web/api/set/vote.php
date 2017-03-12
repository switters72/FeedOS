<?
include("../session.php");

if(!isset($_GET['resource_type']))
	exit(json_encode(array("successful" => false, "error" => "resource_type not provided")));

if(!validResourceType($_GET['resource_type']))
	exit(json_encode(array("successful" => false, "error" => "not a valid resource type (must be 'post' or 'comment'")));

if(!isset($_GET['resource_id']))
	exit(json_encode(array("successful" => false, "error" => "resource_id not provided")));

if(!isset($_GET['vote']))
	exit(json_encode(array("successful" => false, "error" => "vote was not provided")));

if(!validVoteValue($_GET['vote']))
	exit(json_encode(array("successful" => false, "error" => "invalid vote value (must be -1, 0, or 1)")));

if(!validResourceId($_GET['resource_type'], $_GET['resource_id']))
	exit(json_encode(array("successful" => false, "error" => "resource_id was not valid")));

if(userAlreadyVoted($user['id'], $_GET['resource_type'], $_GET['resource_id'])) {
	modifyExistingVote($user['id'], $_GET['resource_type'], $_GET['resource_id'], $_GET['vote']);
} else {
	insertNewVote($user['id'], $_GET['resource_type'], $_GET['resource_id'], $_GET['vote']);
}
exit(json_encode(array("successful" => true)));

function validResourceType($resourceType) {
	if($resourceType != "post" && $resourceType != "comment")
		return false;
	return true;
}

function validVoteValue($voteValue) {
	if($voteValue > 1 || $voteValue < -1)
		return false;
	return true;
}

function insertNewVote($userid, $resourceType, $resourceId, $vote) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("INSERT INTO `anonkhdi_core`.`votes` (`id`, `owner_id`, `resource_type`, `resource_id`, `vote`) VALUES (NULL, '".$userid."', '".$resourceType."', '".$resourceId."', '".$vote."');") or trigger_error(mysql_error());	
}

function modifyExistingVote($userid, $resourceType, $resourceId, $voteValue) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("UPDATE  `anonkhdi_core`.`votes` SET  `vote` =  '".$voteValue."' WHERE `owner_id` = ".$userid." AND `resource_type` = '".$resourceType."' AND `resource_id` = '".$resourceId."';") or trigger_error(mysql_error());
}

function userAlreadyVoted($userid, $resourceType, $resourceId) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("SELECT * FROM `votes`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($row['owner_id'] == $userid && $row['resource_type'] == $resourceType && $row['resource_id'] == $resourceId)
			return true;
	}
	return false;
}

function validResourceId($resourceType, $resourceId) {
	switch($resourceType) {
		case "post":
			return validPostId($resourceId);
		case "comment":
			return validCommentId($commentId);
	}
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
	
function validCommentId($commentId) {
	mysql_connect(DB_HOST, DB_USER, DB_PASS) or
		die("Could not connect: " . mysql_error());
	mysql_select_db(DB_NAME);
	
	$result = mysql_query("SELECT * FROM `comments`") or trigger_error(mysql_error());
	while ($row = mysql_fetch_array($result)) {
		if($row['id'] == $commentId)
			return true;
	}
	return false;
}
	
?>