package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.UserCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        final ModelAndView mv = new ModelAndView("user/login");

        return mv;
    }

    @RequestMapping(path = "/user/create", method = RequestMethod.GET)
    public ModelAndView showUserCreateForm(@ModelAttribute("userCreateForm") final UserCreateForm userCreateForm) {

        return new ModelAndView("user/create");
    }

    @RequestMapping(path = "/user/create", method = RequestMethod.POST)
    public ModelAndView register(@Valid @ModelAttribute("userCreateForm") final UserCreateForm userCreateForm, final BindingResult bindingResult,
                                  final RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()){
            // TODO: Delete comment ? (tobi)
            /*if(bindingResult.hasGlobalErrors())
                bindingResult.addError(new FieldError(bindingResult.getObjectName(), "password", "{javax.validation.constraints.PasswordEqualConstraint.message}"));*/
            return showUserCreateForm(userCreateForm);
        }

        final User user = userService.register(userCreateForm.getUsername(), userCreateForm.getPassword(), userCreateForm.getName(), userCreateForm.getEmail());

        //autoLogin(request, authManager, user);

        redirectAttributes.addFlashAttribute("user", user);

        // TODO: Temporal hasta tener autoLogin. Deberia ser a /user/profile
        return new ModelAndView("redirect:/user/" + user.getId());
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView view(HttpServletRequest request, @PathVariable final long userId) {

        final ModelAndView mv = new ModelAndView("user/view");

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if(inputFlashMap == null || !inputFlashMap.containsKey("user"))
            mv.addObject("user", userService.findById(userId)
                .orElseThrow(UserNotFoundException::new));

        return mv;
    }

    @RequestMapping(path = "/user/profile", method = RequestMethod.GET)
    public ModelAndView profile(Principal principal) {

        final ModelAndView mv = new ModelAndView("user/profile");

        mv.addObject("user", userService.findByUsername(principal.getName())
                .orElseThrow(UserNotFoundException::new));

        return mv;
    }

    // TODO: Consultar por una mejor manera, que involucre a Spring Security. No tengo AuthenticationManager
//    private void autoLogin(HttpServletRequest request, AuthenticationManager authManager, User user) {
//
//        Authentication token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
//            user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList()));
//
//        Authentication authentication = authManager.authenticate(token);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        //this step is important, otherwise the new login is not in session which is required by Spring Security
//        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
//
//    }


}