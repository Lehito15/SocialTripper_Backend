package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.entities.Post;
import com.socialtripper.restapi.nodes.PostNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za mapowanie pomiędzy encją postu {@link Post},
 * węzłem postu {@link PostNode},
 * a data transfer object {@link PostDTO}.
 */
@Component
public class PostMapper {
    /**
     * Komponent odpowiedzialny za mapowanie częściowej informacji o koncie użytkownika.
     */
    private AccountThumbnailMapper accountThumbnailMapper;

    /**
     * Setter wstrzykujący komponent mapujący częściowe informacje o koncie.
     * @param accountThumbnailMapper komponent odpowiedzialny za mapowanie częściowej informacji o koncie użytkownika
     */
    @Autowired
    public void setAccountThumbnailMapper(AccountThumbnailMapper accountThumbnailMapper) {
        this.accountThumbnailMapper = accountThumbnailMapper;
    }

    /**
     * Metoda mapująca data transfer object postu do encji.
     *
     * @param postDTO data transfer object postu
     * @return encja postu
     */
    public Post toEntity(PostDTO postDTO) {
        if (postDTO == null) return null;
        return new Post(
                        postDTO.uuid(),
                        postDTO.content(),
                        postDTO.isPublic());
    }


    /**
     * Metoda mapująca encję postu do data transfer object.
     *
     * @param post encja postu
     * @return data transfer object postu
     */
    public PostDTO toDTO(Post post) {
        if (post == null) return null;
        return new PostDTO(
                post.getUuid(),
                post.getContent(),
                post.isPublic(),
                post.getDateOfPost(),
                post.isExpired(),
                post.isLocked(),
                post.getCommentsNumber(),
                post.getReactionsNumber(),
                accountThumbnailMapper.toDTO(post.getAccount()),
                null
        );
    }

    /**
     * Metoda mapująca encję oraz węzeł postu do data transfer object.
     *
     * @param post encja postu
     * @param postNode węzeł postu
     * @return data transfer object postu
     */
    public PostDTO toDTO(Post post, PostNode postNode) {
        if (postNode == null) return null;
        return new PostDTO(
                postNode.getUuid(),
                postNode.getContent(),
                post.isPublic(),
                postNode.getPostTime(),
                post.isExpired(),
                post.isLocked(),
                postNode.getCommentsNumber(),
                postNode.getReactionsNumber(),
                accountThumbnailMapper.toDTO(post.getAccount()),
                postNode.getMultimediaUrls()
        );
    }

    /**
     * Metoda mapująca encję postu do węzłą postu.
     *
     * @param post encja postu
     * @return węzeł postu
     */
    public PostNode toNode(Post post) {
        if (post == null) return null;
        return new PostNode(
                post.getUuid(),
                post.getContent(),
                post.getDateOfPost()
        );
    }

    /**
     * Metoda kopiująca pola data transfer object postu, które nie są związane innymi encjami do encji postu.
     *
     * @param post encja postu, do której mają zostać przekopiowane wartości pól
     * @param postDTO data transfer object postu, z którego mają zostać przekopiowane wartości pól
     * @return encja postu
     */
    public Post copyNonNullValues(Post post, PostDTO postDTO) {
        if (postDTO == null) return null;
        if (postDTO.uuid() != null) post.setUuid(postDTO.uuid());
        if (postDTO.content() != null) post.setContent(postDTO.content());
        if (postDTO.isPublic() != null) post.setPublic(postDTO.isPublic());
        if (postDTO.isExpired() != null)  post.setExpired(postDTO.isExpired());
        if (postDTO.isLocked() != null)  post.setLocked(postDTO.isLocked());
        if (postDTO.commentsNumber() != null) post.setCommentsNumber(postDTO.commentsNumber());
        if (postDTO.reactionsNumber() != null) post.setReactionsNumber(postDTO.reactionsNumber());
        return post;
    }
}
