/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	config.language = 'zh-cn';
    // 背景颜色
	config.uiColor = '#AADC6E';
    config.font_names = '宋体/宋体;黑体/黑体;仿宋/仿宋;楷体/楷体;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;PingFangSC-Regular' + config.font_names;
    // 上传图片路径
    // config.filebrowserImageUploadUrl = "cityFile/uploadColumnPic";
    // 取消 “拖拽以改变尺寸”功能 plugins/resize/plugin.js
    config.resize_enabled = true;
    config.width = 900;
    config.toolbarCanCollapse = true;
    config.extraPlugins += (config.extraPlugins ? ',lineheight' : 'lineheight');
// 编辑器样式，有三种：'kama'（默认）、'office2003'、'v2'
    config.toolbar = 'Full';
    config.toolbar_Full =
        [
            ['Source','NewPage','Preview','-','Templates'],
            ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
            ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
            '/',
            ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
            ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
            ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
            ['Link','Unlink','Anchor'],
            ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
            '/',
            ['Styles','Format','Font','FontSize','lineheight'],
            ['TextColor','BGColor'],
            ['Maximize', 'ShowBlocks','-','About']
        ];
/*//工具栏是否可以被收缩
    config.toolbarCanCollapse = true;

//工具栏的位置
    config.toolbarLocation = 'top';//可选：bottom

//工具栏默认是否展开
    config.toolbarStartupExpanded = true;

// 取消 “拖拽以改变尺寸”功能 plugins/resize/plugin.js
    config.resize_enabled = true;

//改变大小的最大高度
    config.resize_maxHeight = 3000;

//改变大小的最大宽度
    config.resize_maxWidth = 3000;

// 当提交包含有此编辑器的表单时，是否自动更新元素内的数据
    config.autoUpdateElement = true;*/
};
