/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 * @author Samuele
 */
public class PageHelper {
    private String head, body;
    
    public PageHelper(String title) {
        this.head = "<head>\n"
                + "<meta charset='utf-8'>\n"
                + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n"
                + "<meta name='description' content=''>\n"
                + "<meta name='author' content=''>\n"
                + "<!-- stili -->\n"
                + "<link href='css/bootstrap.css' rel='stylesheet' media='screen'>\n"
                + "<link href='css/bootstrap.min.css' rel='stylesheet' media='screen'>\n"
                + "<link href='css/bootstrap-responsive.css' rel='stylesheet' media='screen'>\n"
                + "<link href='css/bootstrap-responsive.min.css' rel='stylesheet' media='screen'>\n"
                + "<link href='css/style.css' rel='stylesheet' media='screen'>\n"
                + "<!-- script -->\n"
                + "<script type='text/javascript' src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js\"></script>\n"
                + "<script type=\"text/javascript\" src=\"js/bootstrap.min.js\"></script>\n"
                + "<title>"+ title +"</title>\n"
                + "</head>\n";
        this.body = "";
    }
    public void addContent(String content){
        this.body += content+"\n";
    }
    
    public String getFullPage(){
        String page = "<!DOCTYPE html>\n"
                + "<html>\n"
                + this.head
                + "<body>\n"
                + this.body
                +"</body>\n"
                + "</html>";
        return page;
    }
    
    
}
