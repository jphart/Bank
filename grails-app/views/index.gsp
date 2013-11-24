<html>
    <head>
        <title>Bank example</title>
        <meta name="layout" content="main" />
        <style type="text/css" media="screen">

        #nav {
            margin-top:20px;
            margin-left:30px;
            width:228px;
            float:left;

        }
        .homePagePanel * {
            margin:0px;
        }
        .homePagePanel .panelBody ul {
            list-style-type:none;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody h1 {
            text-transform:uppercase;
            font-size:1.1em;
            margin-bottom:10px;
        }
        .homePagePanel .panelBody {
            background: url(images/leftnav_midstretch.png) repeat-y top;
            margin:0px;
            padding:15px;
        }
        .homePagePanel .panelBtm {
            background: url(images/leftnav_btm.png) no-repeat top;
            height:20px;
            margin:0px;
        }

        .homePagePanel .panelTop {
            background: url(images/leftnav_top.png) no-repeat top;
            height:11px;
            margin:0px;
        }
        h2 {
            margin-top:15px;
            margin-bottom:15px;
            font-size:1.2em;
        }
        #pageBody {
            margin-left:2em;
            margin-right:2em;
        }
        </style>
    </head>
    <body>
        <div id="pageBody">
            <h1>Bank Example</h1>
            

            <div >
                <h2>Available Actions</h2>
                <ul>
                        <li><g:link controller="transaction" action="pay">Create a transaction</g:link></li>
                        <li><g:link controller="transaction" action="userTransactions">View transactions</g:link></li>
                        <li><g:link controller="account">Manage accounts</g:link></li>
                </ul>
            </div>
        </div>
    </body>
</html>
