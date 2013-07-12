<?php

## for file
$file = fopen("ret.txt", "r") or exit("Unable to open file!");
$line = "";
$param = "";
while (!feof($file)) {
    $line = fgets($file);
    $fds = explode(" ",$line);
    $kv = explode("=",$fds[0]);
    $uid = $kv[1];

    $kv = explode("=",$fds[1]);
    $block_id = $kv[1];

    $kv = explode("=",$fds[2]);
    $jid = $kv[1];
    echo $uid . $block_id . $jid;
}
fclose($file);
?>