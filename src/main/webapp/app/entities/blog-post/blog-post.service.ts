import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBlogPost } from 'app/shared/model/blog-post.model';

type EntityResponseType = HttpResponse<IBlogPost>;
type EntityArrayResponseType = HttpResponse<IBlogPost[]>;

@Injectable({ providedIn: 'root' })
export class BlogPostService {
    public resourceUrl = SERVER_API_URL + 'api/blog-posts';

    constructor(private http: HttpClient) {}

    create(blogPost: IBlogPost): Observable<EntityResponseType> {
        return this.http.post<IBlogPost>(this.resourceUrl, blogPost, { observe: 'response' });
    }

    update(blogPost: IBlogPost): Observable<EntityResponseType> {
        return this.http.put<IBlogPost>(this.resourceUrl, blogPost, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBlogPost>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBlogPost[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
