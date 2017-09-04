2017.09.04	MySql folder with procedure scripts.

2017.09.03	MySQL folder with scripts for tables.
		PHP folder with scripts for tables.

2017.09.02	Uploading results on server.

2017.09.01	Getting boards from server.
		Storing boards from asynctask to sqllite database.

2017.08.31	Fetching boards when acquiring tournament.
		TODO: Check if boards exists and then insert them if needed

2017.08.15	New table IGRACI
		CursorWrapper created, schema updated.
		PlayerDevice class created.
		PlayerDevice activity created.

2017.08.06	New table TURNIRI_BORDOVI
		Intent arguments used for transferring tournament and board UUID.
		Board UUID presented on fragment board.

2017.08.05	New layout for board presentation.
		BoardShowActivity created.
		BoardShowFragment created.
		Show board item added to menu boardfragment.xml.

2017.07.25	Board layout tuning.
		Bridge score calculated automatically.

2017.07.23	Solved bug with SQL upgrade code for BridgeBoards.db database in BridgeBaseHelper java class.
		Tournament list made starting activity.
		Board list singleton accepts Tournament UUID as parameter.
		Menu option for tournament list is deleted.
		TODO: Change data
		TODO: Check if UUID is ok

2017.07.22:	Added is_bye field do Board table.
		Bye checkbox added to layout for board.
		Bye icon handled for list_item_board.

2017.07.17:	CursorWrapper for tournamnet table
		BridgeBoardsHelper extended for tournamnet table
		BridgeBoardsSchema extender for tournamnet table

2017.07.16:	Tournament object.
		TournamentList Activity and Fragment.
		Menu option for list of tournaments.
		RecyclerView implemented for TournamentList fragment.
		AsyncTask for fetching tournaments from internet.
		Loading tournament data from json_turniri.php.
		activity_tournaments layout.
		fragment_tournament layout.
		list_item_tournament layout.

2017.07.11:	Entry integer value error corrected: tryStrtoInt().
		New layout for board fragment.

2017.07.10:	Database BridgeBoards created.
		Helper class created.
		Schema class created.
		Changed app style to Appcompat.Theme.Light
		Toolbar added.
		Add new Board option.
		Delete item operation.
		Applied menu for board fragment.
		Boards are stored in database.
		Boards are read from database.
		Added getters and setters for Tournament UUID and Pair number.
		New board gets new board UUID automatically.
		Object is stored when edited and app is paused or closed.
		Cursor wrapper class created.
		New board fields updated in app.

2017.07.09:	New item layout organization.
		Temporary icon for NS pair positioning.
		Open Board activity from board list fragment.
		ViewPager implemented in application.
		BoardActivity removed from project as obsolete.

2017.07.08:	SingleFragmentActivity
		TournamentActivity
		TournamentFragment
		BoardActivity
		BoardFragment
		RecyclerView
		OnClick implemented

2017.07.07:	ReadMe.txt
		Board fragment created with three widgets.