<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Simple Layout Demo</title>
        <!--<script type="text/javascript" src="<c:url value='/js/splitpane/debug.js'/>"></script>-->                
        <script src="<c:url value='/js/jquery.min.js'/>"></script>        
        <script type="text/javascript" src="<c:url value='/js/splitpane/jquery-ui-latest.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/js/splitpane/jquery.layout-1.3.0.rc30.80.js'/>"></script>
        <link rel="stylesheet" href="<c:url value='/css/splitpane/layout-default-latest.css'/>" />
        <style type="text/css">
            /*
             *	NOTE: All CSS is purely cosmetic - it does not affect functionality
             */

            /* customize borders to avoid double-borders around inner-layouts */
            .ui-layout-pane {
                border:			0; /* override layout-default-latest.css */
                border-top:		1px solid #BBB;
                border-bottom:	1px solid #BBB;
            }
            .ui-layout-pane-north ,
            .ui-layout-pane-south {
                border:			1px solid #BBB;
                overflow:		hidden;
            }
            .ui-layout-pane-west ,
            .ui-layout-pane-east {
            }
            .ui-layout-pane-center	{
                border-left:	0;
                border-right:	0;
            }
            .inner-center {
                border:		1px solid #BBB;
            }

            /* add shading to outer sidebar-panes */
            .outer-west ,
            .outer-east {
                background-color: #EEE;
            }
            .middle-west ,
            .middle-east {
                background-color: #F8F8F8;
            }

            /* remove padding & scrolling from panes that are 'containers' for nested layouts */
            .outer-center ,
            .middle-center {
                border:			0; /* cosmetic */
                padding:		0;
                overflow:		hidden;
            }

            /*
             *	customize borders on panes/resizers to make pretty
             */
            .ui-layout-pane-west		{ border-right:		0; }
            .ui-layout-resizer-west		{ border-left:		1px solid #BBB; }
            .ui-layout-pane-east		{ border-left:		0; }
            .ui-layout-resizer-east		{ border-right:		1px solid #BBB; }
            .ui-layout-pane-north		{ border-bottom:	0; }
            .ui-layout-resizer-north	{ border-top:		1px solid #BBB; }
            .ui-layout-pane-south		{ border-top:		0; }
            .ui-layout-resizer-south	{ border-bottom: 	1px solid #BBB; }
            /*
             *	add borders to resizers when pane is 'closed'
             *
             *.ui-layout-resizer-closed	{ border:			1px solid #BBB; }
             */
            /*
             *	show both borders when the resizer is 'dragging'
             */
            .ui-layout-resizer-west-dragging ,
            .ui-layout-resizer-east-dragging {
                border-left:		1px solid #BBB;
                border-right:		1px solid #BBB;
            }
            .ui-layout-resizer-north-dragging ,
            .ui-layout-resizer-south-dragging {
                border-top:		1px solid #BBB;
                border-bottom:	1px solid #BBB;
            }


        </style>
        <!-- LAYOUT v 1.3.0 -->

        <script type="text/javascript">

            var myLayout;

            $(document).ready(function () {

                // OUTER-LAYOUT
                $('body').layout({
                    center__paneSelector: ".outer-center"
                    , west__paneSelector: ".outer-west"
                    , east__paneSelector: ".outer-east"
                    , west__size: 125
                    , east__size: 125
                    , spacing_open: 8  // ALL panes
                    , spacing_closed: 12 // ALL panes
                            //,	north__spacing_open:	0
                            //,	south__spacing_open:	0
                    , north__maxSize: 200
                    , south__maxSize: 200

                            // MIDDLE-LAYOUT (child of outer-center-pane)
                    , center__childOptions: {
                        center__paneSelector: ".middle-center"
                        , west__paneSelector: ".middle-west"
                        , east__paneSelector: ".middle-east"
                        , west__size: 100
                        , east__size: 100
                        , spacing_open: 8  // ALL panes
                        , spacing_closed: 12 // ALL panes

                                // INNER-LAYOUT (child of middle-center-pane)
                        , center__childOptions: {
                            center__paneSelector: ".inner-center"
                            , west__paneSelector: ".inner-west"
                            , east__paneSelector: ".inner-east"
                            , west__size: 75
                            , east__size: 75
                            , spacing_open: 8  // ALL panes
                            , spacing_closed: 8  // ALL panes
                            , west__spacing_closed: 12
                            , east__spacing_closed: 12
                        }
                    }
                });

            });
        </script>
    </head>
    <body>
        <div class="outer-center">
            <div class="middle-center">
                <div class="inner-center">Inner Center</div>
                <div class="ui-layout-south">Inner South</div>
            </div>
            <div class="middle-west">Middle West</div>
        </div>
    </body>
</html>
