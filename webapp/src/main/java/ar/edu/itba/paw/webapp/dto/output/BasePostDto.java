package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.models.Role;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.util.Collection;

public abstract class BasePostDto {

    public static UriBuilder getPostUriBuilder(Post post, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("posts").path(String.valueOf(post.getId()));
    }

    private long id;
    private LocalDateTime creationDate;
    private String title;
    private Integer wordCount;
    private Boolean edited;
    private LocalDateTime lastEditDate;
    private LightweightUserDto user;
    private PostCategoryDto postCategory;
    private Collection<String> tags;
    private Boolean isOwner;
    private boolean enabled;

    // Relations
    private String comments;
    private String votes;

    private String url;

    public BasePostDto() {
        // For Jersey - Do not use
    }

    public BasePostDto(Post post, UriInfo uriInfo, SecurityContext securityContext) {

        final UriBuilder postUriBuilder = getPostUriBuilder(post, uriInfo);

        id = post.getId();
        enabled = post.isEnabled();
        url = postUriBuilder.build().toString();

        if(isProtected(securityContext))
            return;

        creationDate = post.getCreationDate();
        title = post.getTitle();

        wordCount = post.getWordCount();
        edited = post.isEdited();
        lastEditDate = post.getLastEditDate();
        user = new LightweightUserDto(post.getUser(), uriInfo, securityContext);
        postCategory = new PostCategoryDto(post.getCategory());
        tags = post.getTags();

        if(securityContext.getUserPrincipal() != null) {
            isOwner = post.getUser().getUsername().equals(securityContext.getUserPrincipal().getName());
        }

        comments = postUriBuilder.clone().path("comments").build().toString();
        votes = postUriBuilder.clone().path("votes").build().toString();
    }

    protected boolean isProtected(SecurityContext securityContext) {
        return !enabled && !securityContext.isUserInRole(Role.ADMIN.name());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Boolean isEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public LocalDateTime getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(LocalDateTime lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public LightweightUserDto getUser() {
        return user;
    }

    public void setUser(LightweightUserDto user) {
        this.user = user;
    }

    public PostCategoryDto getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(PostCategoryDto postCategory) {
        this.postCategory = postCategory;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
