//
//  EventRow.swift
//  iosApp
//
//  Created by Steve Emmerich on 8/25/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared


struct EventRow: View {
    var event: Event

    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("Event ID:  \(event.ID)")
                //Text("Event Text: \(event.eventText)")
            }
            Spacer()
        }
    }
}



