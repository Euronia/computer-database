package com.excilys.formation.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.pagination.Page;

public class PaginationTag extends SimpleTagSupport{
    @Override
    public void doTag() {
        Page<ComputerDto> pageComputer = (Page<ComputerDto>) getJspContext().getAttribute("pageComputer", PageContext.APPLICATION_SCOPE);
        JspWriter writer = getJspContext().getOut();
        StringBuilder string = new StringBuilder();
        string.append("<div class=\"container\" style=\"margin-top: 10px;\"> <table class=\"table table-striped table-bordered\">" +
        "<thead><tr><!-- Variable declarations for passing labels as parameters --> <!-- Table header for Computer Name --> "+
        "<th class=\"editMode\" style=\"width: 60px; height: 22px;\"><input type=\"checkbox\" id=\"selectall\" /> " +
        "<span style=\"vertical-align: top;\"> -  <a href=\"#\" id=\"deleteSelected\" onclick=\"$.fn.deleteSelected();\"> " +
        "<i class=\"fa fa-trash-o fa-lg\"></i></a></span></th><th> Computer name </th> " +
        "<th> Introduced date </th><!-- Table header for Discontinued Date --><th> Discontinued date </th> " +
        "<!-- Table header for Company --><th> Company </th></tr></thead>");
        for( ComputerDto computerDto : pageComputer.elements)
        {
            string.append("<tr><td class=\"editMode\"><input type=\"checkbox\" name=\"cb\" class=\"cb\" value=\"0\"> </td> ");
            string.append("<td> <a href=\"editComputer.htmp\" onclick=\"\">" + computerDto.name + 
                    "</a></td><td>");
            if (computerDto.introduced != null) {
            string.append(computerDto.introduced);
            }
            string.append("</td><td>");
            if (computerDto.discontinued != null) {
            string.append(computerDto.discontinued);
            }
            string.append("</td><td>");
            if (computerDto.companyName != null) {
            string.append(computerDto.companyName);
            }
            string.append("</td> </tr>");
        }
        string.append("</tbody></table></div>");
        try {
            writer.println(string.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
