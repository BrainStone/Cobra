/*
 * Copyright (c) 2016 "JackWhite20"
 *
 * This file is part of Cobra.
 *
 * Cobra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import de.jackwhite20.cobra.server.filter.FilteredRequest;
import de.jackwhite20.cobra.server.filter.RequestFilter;
import de.jackwhite20.cobra.shared.http.Response;
import de.jackwhite20.cobra.shared.Status;

/**
 * Created by JackWhite20 on 31.01.2016.
 */
public class TestFilter implements RequestFilter {

    @Override
    public void filter(FilteredRequest request) {

        if(request.header("X-Test") != null)
            request.abortWith(Response.status(Status.FORBIDDEN).build());
    }
}
