$(function () {
	$(".question h2").click(function (e) {
		e.preventDefault();
		//在 h2 上動態新增 class
		$(this).toggleClass("active");
		//h2 在父層元素中，找到 p 元素，並且給它滑動效果
		$(this).parent().find("p").slideToggle();
		//h2 在父層 .question 元素中，找到其他 .question 同層元素，再找到該同層元素的 p 標籤，並向上收闔
		$(this).parent().siblings().find("p").slideUp();
		//h2 在父層 .question 元素中，找到其他 .question 同層元素，再找到該同層元素的 h2 標籤，並動態移除 class
		$(this).parent().siblings().find("h2").removeClass("active");
	});
});