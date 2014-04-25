import geb.Page
import geb.spock.GebSpec

class BananaScrumLogInSpec extends GebSpec {

    def open = this.&to

    def setup() {
        Page.mixin PageExtension
    }

    def "should login to BananaScrum using PageObjectPattern"() {
        when:
        open(LoginPage).logIn("admin", "password")

        then:
        isAt(BacklogPage)
    }

    def "should not be possible to login using wrong credentials"(username, password) {
        when:
        open(LoginPage).logIn(username, password)

        then:
        $("#flash").text() == "Login failed"

        where:
        username | password
        "admin"  | "passwd"
        "user"   | "password"
        "user"   | "passwd"
    }
}

class LoginPage extends Page {
    static url = "https://szkolenia.bananascrum.com/login"

    def logIn(username, passwd) {
        login = username
        password = passwd
        commit().click()

        navigateTo(BacklogPage)
    }
}

class BacklogPage extends Page {
    static at = { $(".backlog-header-title").text() == "Product backlog" }
}

class PageExtension {
    def navigateTo(Class<? extends Page> page) {
        browser.page(page)
        browser.page
    }
}