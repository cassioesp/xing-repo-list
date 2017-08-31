XING's GitHub Repositories
=============================

This android app was developed to test some functions: 
- Pagination in a ListView
- Acess to a Web Service
- JSON manipulation
- ListView/ViewHolder Pattern manipulation
- Request Browser acess from android app

This simple app do these stuffs like below:
-----
1. Request the GitHub API to show [XING's public repositories][1] and parse the JSON
   response.
2. Display a list of repositories, each entry should show
    - repo name
    - description
    - login of the owner
3. Request only 10 repos at a time. Use an endless list with a load more mechanism. The
   load more should be triggered when the scrolling is close to reaching the end of the
list.
4. Show a light green background if the `fork` flag is false or missing, a white one
   otherwise.
5. On a long click on a list item show a dialog to ask if go to repository `html_url` or
   owner `html_url` which is opened then in the browser.

