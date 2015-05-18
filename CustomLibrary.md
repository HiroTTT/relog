CustomLibraryプロジェクトの説明


# プロジェクト構造 #

## jp.xii.relog.customlibrary ##
- DoSuCommand.java<br>
super userでコマンドを実行します<br>
簡易なのでコマンドの実行と結果の受信が直列です。<br>
- EulaHelper.java<br>
EULAの表示ヘルパー<br>
一度同意すると次回以降表示しないようにしてくれます。<br>
サンプルのEULA付き。<br>
string_eula.xmlの内容を修正すること<br>
- NotifyControl.java<br>
通知領域へ情報を出力します<br>

<h2>jp.xii.relog.customlibrary.app</h2>
- FileListDialog.java<br>
ファイル選択ダイアログ<br>

<h2>jp.xii.relog.customlibrary.mediastore</h2>
- MediaScannerNotifier.java<br>
- MediaStoreAccess.java<br>
- MediaStoreAccessAudio.java<br>

<h2>jp.xii.relog.customlibrary.preference</h2>
- FileSelectPreference.java<br>
ファイルを選択できる<br>
- IpAddressPreference.java<br>
IPアドレスを設定できる<br>
- OriginalDialogPreference.java<br>
オリジナルのプリファレンス作成時の基底クラス<br>
- RangeSelectPreference.java<br>
範囲を設定できる<br>
- TimePickerPreference.java<br>
時間を設定できる<br>

<h2>jp.xii.relog.customlibrary.view</h2>
- OriginalPreferenceActivity.java<br>
プリファレンスを作成する時にsuperクラスにすると便利なクラス<br>
- OriginalView.java<br>
カスタムビューを作る時のベースクラス<br>

<h2>jp.xii.relog.customlibrary.widget</h2>
- BreadCrumbList.java<br>
パンくずリスト<br>
- FileListView.java<br>
ファイル選択用ビュー<br>
- HorizontalAutoScrollTextView.java<br>
横に自動スクロールするテキストビュー<br>
- HorizontalAutoScrollView.java<br>
横に自動スクロールするビュー<br>
- ProgressBar.java<br>
残骸？<br>
- ProgressCircle.java<br>
円形のプログレスバー<br>
- RangeSelectBar.java<br>
範囲を選択できるSeekbar的なもの<br>